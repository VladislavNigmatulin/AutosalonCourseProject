package ejb;

import model.Role;

import javax.ejb.Local;

@Local
public interface CommonEJBBeanLocal {
    public Role getRoleByTitle(String roleTitle);
}
