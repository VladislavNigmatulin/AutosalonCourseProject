package ejb;

import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AdminEJBBeanLocal {
    public List<User> getListOfClients();
    public List<User> getListOfManagers();
}
