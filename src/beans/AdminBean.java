package beans;

import ejb.AdminEJBBean;
import ejb.AdminEJBBeanLocal;
import model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AdminBean {

    @EJB
    private AdminEJBBeanLocal adminEJBBean;

    private List<User> listOfClients;

    private List<User> listOfManagers;

    public List<User> getListOfManagers() {
        listOfManagers = adminEJBBean.getListOfManagers();
        return listOfManagers;
    }

    public List<User> getListOfClients() {
        listOfClients = adminEJBBean.getListOfClients();
        return listOfClients;
    }
}
