package beans;

import ejb.ClientEJBBeanLocal;
import ejb.CommonEJBBeanLocal;
import model.Car;
import model.Model;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AutosalonShowCarsBean implements Serializable {

    @EJB
    private ClientEJBBeanLocal clientEJBBean;

    @EJB
    private CommonEJBBeanLocal commonEJBBean;

    private List<Car> listOfCars = new ArrayList<Car>();

    private Car selectedCar;

    public List<Car> getListOfCars() {
        listOfCars = clientEJBBean.getListOfCars();
        return listOfCars;
    }

    public Model findModelOfCar(Car car){
        Model model = commonEJBBean.findModelById(car.getModelId());
        return model;
    }

    public String goToInfoAboutCar(Car car){
        selectedCar = car;
        return "showInfoAboutAutosalonCar";
    }

    public Car getSelectedCar() {
        return selectedCar;
    }
}
