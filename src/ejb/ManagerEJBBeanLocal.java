package ejb;

import model.Diler;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ManagerEJBBeanLocal {
    public List<Diler> getListOfDilers();
}
