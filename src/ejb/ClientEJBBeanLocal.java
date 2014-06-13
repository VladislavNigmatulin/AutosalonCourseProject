package ejb;

import model.Car;
import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ClientEJBBeanLocal {
    public List<Car> getListOfCars();
    public boolean makeOrder(User userOnline, Car selectedCar);
}
