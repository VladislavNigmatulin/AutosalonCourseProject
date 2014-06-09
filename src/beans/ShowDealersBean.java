package beans;

import ejb.ManagerEJBBeanLocal;
import model.Car;
import model.Carforsupply;
import model.Diler;
import model.Model;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ShowDealersBean implements Serializable {

    @EJB
    private ManagerEJBBeanLocal managerEJBBean;

    private List<Diler> listOfDilers;

    private List<Model> listOfModels;

    private List<Carforsupply> listOfCars;

    private Diler selectedDiler;

    private Model selectedModel;

    public String toToDilerPage(){
        return "showDealerPage";
    }

    public String showModelsOfConcretDiler(Diler diler){
        this.selectedDiler = diler;
        return "modelsOfDealerPage";
    }

    public String showCarsForModels(Model model){
        this.selectedModel = model;
        return "carForSupAvailablePage";
    }

    public List<Diler> getListOfDilers() {
        listOfDilers = managerEJBBean.getListOfDilers();
        return listOfDilers;
    }

    public Diler getSelectedDiler() {
        return selectedDiler;
    }

    public void setSelectedDiler(Diler selectedDiler) {
        this.selectedDiler = selectedDiler;
    }

    public Model getSelectedModel() {
        return selectedModel;
    }

    public List<Model> getListOfModels() {
        listOfModels = selectedDiler.getModels();
        return listOfModels;
    }

    public List<Carforsupply> getListOfCars() {
        listOfCars = selectedModel.getCarforsupplies();
        return listOfCars;
    }
}
