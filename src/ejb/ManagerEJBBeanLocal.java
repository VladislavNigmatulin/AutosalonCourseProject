package ejb;

import model.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ManagerEJBBeanLocal {
    public List<Diler> getListOfDilers();
    public int calculateFinalPriceOfCars(List<Carforsupply> cars);
    public boolean makeOrderForSupply(User userOnline, List<Carforsupply> listOfCars);
    public Autosalon findAutosalonByManager(User user);
    public List<Trade> showDilersSalesHistory(int dilerId);
    public Diler findDilerById(int dilerId);
}
