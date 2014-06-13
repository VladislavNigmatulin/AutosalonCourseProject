package ejb;

import model.Model;
import model.Role;

import javax.ejb.Local;

@Local
public interface CommonEJBBeanLocal {
    public Role getRoleByTitle(String roleTitle);
    public Model findModelById(int modelId);
}
