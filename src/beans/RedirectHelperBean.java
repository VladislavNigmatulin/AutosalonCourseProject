package beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RedirectHelperBean {

    @Inject
    private AuthorizationBean regAndAuthBean;

    public String goToClientPage(){
        String helpLogon = helpLogon();
        if (helpLogon.equals("success")){
            if (regAndAuthBean.getPermissions().equals("Клиент"))
                return "client/clientPage";
            else return "accessDenied";
        } else
            return "logon";
    }

    public String goToManagerPage(){
        String helpLogon = helpLogon();
        if (helpLogon.equals("success")){
            if (regAndAuthBean.getPermissions().equals("Менеджер"))
                return "managerPage";
            else return "accessDenied";
        } else
            return "logon";
    }

    public String goToAdminPage(){
        String helpLogon = helpLogon();
        if (helpLogon.equals("success")){
            if (regAndAuthBean.getPermissions().equals("Администратор"))
                return "admin/adminPage";
            else return "accessDenied";
        } else
            return "logon";
    }

    public String helpLogon(){
        if (regAndAuthBean.getUserOnline() != null)
            return "success";
        return "logon";
    }
}
