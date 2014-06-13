package beans;

import ejb.ClientEJBBeanLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ClientOrderingBean {

    @Inject
    private AutosalonShowCarsBean autoShowCarsBean;

    @Inject
    private AuthorizationBean authorizationBean;

    @EJB
    private ClientEJBBeanLocal clientEJBBean;

    private boolean resultOfOrdering;

    public String makeOrder(){
        resultOfOrdering = clientEJBBean.makeOrder(authorizationBean.getUserOnline(), autoShowCarsBean.getSelectedCar());
        return "resultOfOrdering";
    }

    public boolean isResultOfOrdering() {
        return resultOfOrdering;
    }
}
