package ejb;

import model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Stateless
public class UserEJBBean implements UserEJBBeanLocal{

    @PersistenceContext(name = "persistence/users", unitName= "UsersPersistenceUnit")
    private EntityManager emU;

    @EJB
    private CommonEJBBeanLocal commonEJBBean;

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
        Predicate predicate2 = userRoot.get("password").in(getHash(password));
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
     * Регистрация нового менеджера, а также привязка его к автосалону
     *@param user - пользователь
     * @param autosalonId - ID автосалона
     */
    @Override
    public void registerNewManager(User user, int autosalonId){
        user.setPassword(getHash(user.getPassword()));
        user.setAutosalon_id(autosalonId);
        user = emU.merge(user);
        Role managerRole = commonEJBBean.getRoleByTitle("Менеджер");
        user.getRoles().add(managerRole);
        managerRole.getUsers().add(user);
        emU.merge(user);
    }

    /**
     * Уволить менеджера
     * @param user - менеджер
     */
    @Override
    public void dismissManager(User user){
        User userFind = emU.find(User.class, user.getId());
        emU.joinTransaction();
        Role managerRole = userFind.getRoles().get(0);
        managerRole.getUsers().remove(userFind);
        emU.remove(userFind);
        emU.flush();
    }

    /**
     * Регистрация нового клиента, а также создание счета для него, и истории для этого счета
     * @param user - пользователь
     */
    @Override
    public void registerNewClient(User user){
        user.setPassword(getHash(user.getPassword()));
        Bill bill = new Bill(0, new Date());
        bill = emU.merge(bill);
        History history = new History("Инициализация", 0);
        history.setBill(bill);
        history = emU.merge(history);
        bill.getHistories().add(history);
        bill = emU.merge(bill);
        user.setBill(bill);
        user = emU.merge(user);
        Role clientRole = commonEJBBean.getRoleByTitle("Клиент");
        user.getRoles().add(clientRole);
        clientRole.getUsers().add(user);
        emU.persist(user);
    }

    /**
     * Подсчет разницы в годах между текущей датой и датой рождения (jodaTime не работает в EJB =((( )
     * @param date = дата рождения
     * @return
     */
    @Override
    public int getDifferenceBeetweenTwoDates(Date date){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        String[] mas = dateString.split(Pattern.quote("-"));
        int yearOfBirhtday = Integer.parseInt(mas[0]);
        int years = 1900 + new Date().getYear() - yearOfBirhtday;
        return years;
    }

    /**
     * Шифрование MD5
     * @param sInput - строка, которую надо зашифровать
     * @return
     */
    public String getHash(String str) {
        MessageDigest md5 ;
        StringBuffer  hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
        }
        catch (NoSuchAlgorithmException e) {
            return e.toString();
        }
        return hexString.toString();
    }
}
