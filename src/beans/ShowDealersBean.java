package beans;

import ejb.ManagerEJBBeanLocal;
import model.*;

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

    private List<Trade> listOfTrades;

    private List<Diler> listOfDilers;

    private List<Model> listOfModels;

    private List<Carforsupply> listOfCars;

    private Diler selectedDiler;

    private Model selectedModel;

    private int selectedDilerId;

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

    public String goToSupplyHistoryPage(){
        selectedDiler = managerEJBBean.findDilerById(selectedDilerId);
        return "supplyHistoryPage";
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

    public List<Trade> getListOfTrades() {
        listOfTrades = managerEJBBean.showDilersSalesHistory(selectedDilerId);
        return listOfTrades;
    }

    public int getSelectedDilerId() {
        return selectedDilerId;
    }

    public void setSelectedDilerId(int selectedDilerId) {
        this.selectedDilerId = selectedDilerId;
    }
}
