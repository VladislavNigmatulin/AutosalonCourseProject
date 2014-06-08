package ejb;

import model.Bill;
import model.History;
import model.Role;
import model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Stateless
public class UserEJBBean implements UserEJBBeanLocal{

    @PersistenceContext(name = "persistence/users", unitName= "UsersPersistenceUnit")
    private EntityManager emU;

    /**
     * Поиск пользователя по логину и паролю
     * @param login - логин
     * @param password - пароль
     * @return - пользователь/null
     */
    @Override
    public User findUserByLogin(String login, String password){
        CriteriaQuery<User> criteriaQuery = emU.getCriteriaBuilder().createQuery(User.class);
        Root userRoot = criteriaQuery.from(User.class);
        Predicate predicate1 = userRoot.get("login").in(login);
        Predicate predicate2 = userRoot.get("password").in(password);
        criteriaQuery.select(userRoot).where(predicate1);
        criteriaQuery.select(userRoot).where(predicate2);
        List<User> listOfUsers = emU.createQuery(criteriaQuery).getResultList();
        if (listOfUsers.size() != 0 )
            return listOfUsers.get(0);
        else return null;
    }

    /**
     * Поиск пользователя по логину (для валидации)
     * @param login - логин
     * @return
     */
    @Override
    public boolean checkUserWithSameLogin(String login){
        CriteriaQuery<User> criteriaQuery = emU.getCriteriaBuilder().createQuery(User.class);
        Root userRoot = criteriaQuery.from(User.class);
        Predicate predicate1 = userRoot.get("login").in(login);
        criteriaQuery.select(userRoot).where(predicate1);
        List<User> listOfUsers = emU.createQuery(criteriaQuery).getResultList();
        if (listOfUsers.size() != 0)
            return true;
        return false;
    }

    /**
     * Регистрация нового клиента, а также создание счета для него, и истории для этого счета
     * @param user - пользователь
     */
    @Override
    public void registerNewClient(User user){
        Bill bill = new Bill(0, new Date());
        bill = emU.merge(bill);
        History history = new History("Инициализация", 0);
        history.setBill(bill);
        history = emU.merge(history);
        bill.getHistories().add(history);
        bill = emU.merge(bill);
        user.setBill(bill);
        user = emU.merge(user);
        Role clientRole = getClientRole();
        user.getRoles().add(clientRole);
        clientRole.getUsers().add(user);
        emU.persist(user);
    }

    /**
     * Поиск роли "Клиент"
     * @return - роль Клиент
     */
    private Role getClientRole(){
        CriteriaQuery<Role> criteriaQuery = emU.getCriteriaBuilder().createQuery(Role.class);
        Root roleRoot = criteriaQuery.from(Role.class);
        Predicate predicate1 = roleRoot.get("title").in("Клиент");
        criteriaQuery.select(roleRoot).where(predicate1);
        Role clientRole = emU.createQuery(criteriaQuery).getSingleResult();
        return clientRole;
    }


}
