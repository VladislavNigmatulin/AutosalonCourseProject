package ejb;

import model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
}
