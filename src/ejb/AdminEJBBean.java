package ejb;

import model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class AdminEJBBean implements AdminEJBBeanLocal {

    @PersistenceContext(name = "persistence/users", unitName= "UsersPersistenceUnit")
    private EntityManager emU;

    @PersistenceContext(name = "persistence/autosalons", unitName = "AutosalonsPersistenceUnit")
    private EntityManager emA;

    @EJB
    private CommonEJBBeanLocal commonEJBBean;

    public AdminEJBBean(){}

    /**
     * Получить список клиентов
     * @return список клиентов
     */
    @Override
    public List<User> getListOfClients(){
        Role clientRole = commonEJBBean.getRoleByTitle("Клиент");
        List<User> listOfClients = clientRole.getUsers();
        return  listOfClients;
    }

    /**
     * Получить список менеджеров
     * @return список менеджеров
     */
    @Override
    public List<User> getListOfManagers(){
        Role managerRole = commonEJBBean.getRoleByTitle("Менеджер");
        List<User> listOfManagers = managerRole.getUsers();
        return listOfManagers;
    }

    /**
     * Получить список автосалонов
     * @return список автосалонов
     */
    @Override
    public List<Autosalon> getListOfAutosalons(){
        Query query = emA.createQuery("SELECT a FROM Autosalon a");
        List<Autosalon> listOfAutosalons = query.getResultList();
        return listOfAutosalons;
    }

    /**
     * Создать новый автосалон
     * @param autosalon - автосалон
     * @param money - деньги
     */
    @Override
    public void createNewAutosalon(Autosalon autosalon, int money){
        Autosalon autosal = emA.merge(autosalon);
        Date today = new Date();
        Timestamp timestampToday = new Timestamp(today.getTime());
        Autosalonbill bill = new Autosalonbill(timestampToday, money, autosal);
        emA.merge(bill);
    }

}
