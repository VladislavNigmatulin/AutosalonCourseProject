package beans;

import ejb.ClientEJBBeanLocal;
import model.Orderforcar;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ClientOrderingBean {

    @Inject
    private AutosalonShowCarsBean autoShowCarsBean;

    @Inject
    private AuthorizationBean authorizationBean;

    @EJB
    private ClientEJBBeanLocal clientEJBBean;

    private List<Orderforcar> listOfOrders = new ArrayList<Orderforcar>();

    private Orderforcar selectedOrder;

    private boolean resultOfOrdering;

    public String makeOrder(){
        resultOfOrdering = clientEJBBean.makeOrder(authorizationBean.getUserOnline(), autoShowCarsBean.getSelectedCar());
        return "resultOfOrdering";
    }

    public boolean isResultOfOrdering() {
        return resultOfOrdering;
    }

    public List<Orderforcar> getListOfOrders() {
        listOfOrders = clientEJBBean.getListOfOrderforcars(authorizationBean.getUserOnline());
        return listOfOrders;
    }

    public Orderforcar getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Orderforcar selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
}
