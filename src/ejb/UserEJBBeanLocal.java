package ejb;

import model.User;

import javax.ejb.Local;

@Local
public interface UserEJBBeanLocal {
    public User findUserByLogin(String login, String password);
}
