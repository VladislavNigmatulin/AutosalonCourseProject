package ejb;

import model.Role;
import model.User;

import javax.ejb.Local;
import java.util.Date;

@Local
public interface UserEJBBeanLocal {
    public User findUserByLogin(String login, String password);
    public boolean checkUserWithSameLogin(String login);
    public void registerNewClient(User user);
    public Role getClientRole(String roleTitle);
    public int getDifferenceBeetweenTwoDates(Date date);
}
