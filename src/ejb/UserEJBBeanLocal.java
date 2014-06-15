package ejb;

import model.Autosalon;
import model.Role;
import model.User;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface UserEJBBeanLocal {
    public User findUserByLogin(String login, String password);
    public boolean checkUserWithSameLogin(String login);
    public void registerNewClient(User user);
    public void registerNewManager(User user, int autosalonId);
    public int getDifferenceBeetweenTwoDates(Date date);
    public void dismissManager(User user);
}
