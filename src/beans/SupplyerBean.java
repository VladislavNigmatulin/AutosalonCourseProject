package beans;

import ejb.ManagerEJBBeanLocal;
import model.Carforsupply;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class SupplyerBean implements Serializable {

    @Inject
    private AuthorizationBean authorizationBean;

    @EJB
    private ManagerEJBBeanLocal managerBean;

    @Inject
    private ShowDealersBean showDealersBean;

    private List<Carforsupply> listForSupplies = new ArrayList<Carforsupply>();

    private Carforsupply selectedCarforsupply;

    private int newCount;

    private int finalPrice;

    private boolean resultOfSupply;

    public String removeFromListForSupply(Carforsupply carforsupply){
        listForSupplies.remove(carforsupply);
        return "basketOfCarsPage";
    }

    public String addToListForSupply(Carforsupply carforsupply){
        Carforsupply newCar = new Carforsupply(carforsupply.getId(),
                carforsupply.getColor(), carforsupply.getCost(),
                newCount, carforsupply.getModel(), carforsupply.getTrades());
        listForSupplies.add(newCar);
        newCount = 0;
        return showDealersBean.showCarsForModels(showDealersBean.getSelectedModel());
    }

    public boolean checkFromListForSupply(Carforsupply carforsupply){
        for (Carforsupply car : listForSupplies){
            if (car.getModel().getDiler().getTitle().equals(carforsupply.getModel().getDiler().getTitle())
                && (car.getModel().getTitle().equals(carforsupply.getModel().getTitle())) &&
                    (car.getColor().equals(carforsupply.getColor())))
                return true;
        }
        return false;
    }

    public String makeOrderForSupply(){
        resultOfSupply = managerBean.makeOrderForSupply(authorizationBean.getUserOnline(), listForSupplies);
        listForSupplies.clear();
        return "statusOfOrderForSupply";
    }

    public String goToOrderPage(){
        return "supplyOrderPage";
    }

    public List<Carforsupply> getListForSupplies() {
        return listForSupplies;
    }

    public Carforsupply getSelectedCarforsupply() {
        return selectedCarforsupply;
    }

    public void setSelectedCarforsupply(Carforsupply selectedCarforsupply) {
        this.selectedCarforsupply = selectedCarforsupply;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public void validateSelectedCarOnCount(ComponentSystemEvent event){
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();
        UIInput uiCount = (UIInput) component.findComponent("count");
        String countStr = uiCount.getLocalValue() == null ? ""
                : uiCount.getLocalValue().toString();
        String countId = uiCount.getClientId();
        if (!countStr.equals("")){
            int count = Integer.parseInt(countStr);
            if (count > selectedCarforsupply.getCount()){
                FacesMessage msg = new FacesMessage("У дилера нет в наличии столько автомобилей");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(countId, msg);
                fc.renderResponse();
            }
        }
    }

    public int getFinalPrice() {
        finalPrice = managerBean.calculateFinalPriceOfCars(listForSupplies);
        return finalPrice;
    }

    public boolean isResultOfSupply() {
        return resultOfSupply;
    }
}
