package ejb;

import model.Role;
import model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AdminEJBBean implements AdminEJBBeanLocal {

    @PersistenceContext(name = "persistence/users", unitName= "UsersPersistenceUnit")
    private EntityManager emU;

    public AdminEJBBean(){}

    @EJB
    private UserEJBBeanLocal userEJBBean;

    @Override
    public List<User> getListOfClients(){
        Role clientRole = userEJBBean.getClientRole("Клиент");
        List<User> listOfClients = clientRole.getUsers();
        return  listOfClients;
    }

    @Override
    public List<User> getListOfManagers(){
        Role managerRole = userEJBBean.getClientRole("Менеджер");
        List<User> listOfManagers = managerRole.getUsers();
        return listOfManagers;
    }


}
