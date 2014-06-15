package beans;

import ejb.AdminEJBBean;
import ejb.AdminEJBBeanLocal;
import ejb.ManagerEJBBeanLocal;
import ejb.UserEJBBeanLocal;
import model.Autosalon;
import model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AdminBean implements Serializable {

    @EJB
    private AdminEJBBeanLocal adminEJBBean;

    @EJB
    private ManagerEJBBeanLocal managerEJBBean;

    @EJB
    private UserEJBBeanLocal userEJBBean;

    private List<User> listOfClients;

    private List<User> listOfManagers;

    private List<Autosalon> listOfAutosalons;

    private User selectedUser;

    private Autosalon selectedAutosalon;

    public List<User> getListOfManagers() {
        listOfManagers = adminEJBBean.getListOfManagers();
        return listOfManagers;
    }

    public List<User> getListOfClients() {
        listOfClients = adminEJBBean.getListOfClients();
        return listOfClients;
    }

    public String showInfoAboutUser(User user){
        selectedUser = user;
        return "infoAboutManager";
    }

    public String goToDismissManager(User user){
        selectedUser = user;
        return "confirmDismissManager";
    }

    public String dismissManager(){
        userEJBBean.dismissManager(selectedUser);
        listOfManagers.clear();
        listOfManagers = adminEJBBean.getListOfManagers();
        return "showUsersList";
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public Autosalon getSelectedAutosalon() {
        selectedAutosalon = managerEJBBean.findAutosalonByManager(selectedUser);
        return selectedAutosalon;
    }

    public List<Autosalon> getListOfAutosalons() {
        listOfAutosalons = adminEJBBean.getListOfAutosalons();
        return listOfAutosalons;
    }
}
