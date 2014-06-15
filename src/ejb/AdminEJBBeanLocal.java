package ejb;

import model.Autosalon;
import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AdminEJBBeanLocal {
    public List<User> getListOfClients();
    public List<User> getListOfManagers();
    public List<Autosalon> getListOfAutosalons();
    public void createNewAutosalon(Autosalon autosalon, int money);
}
