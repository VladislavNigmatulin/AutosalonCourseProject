package ejb;

import model.Car;
import model.Orderforcar;
import model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ClientEJBBeanLocal {
    public List<Car> getListOfCars();
    public boolean makeOrder(User userOnline, Car selectedCar);
    public List<Orderforcar> getListOfOrderforcars(User user);
}
