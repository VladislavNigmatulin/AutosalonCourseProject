package ejb;

import model.Autosalon;
import model.Carforsupply;
import model.Diler;
import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ManagerEJBBeanLocal {
    public List<Diler> getListOfDilers();
    public int calculateFinalPriceOfCars(List<Carforsupply> cars);
    public boolean makeOrderForSupply(User userOnline, List<Carforsupply> listOfCars);
    public Autosalon findAutosalonByManager(User user);
}
