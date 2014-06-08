package beans;

import ejb.UserEJBBeanLocal;
import model.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Named
@SessionScoped
public class AuthorizationBean implements Serializable {

    @EJB
    private UserEJBBeanLocal userEJBBean;

    private String login;

    private String password;

    private User userOnline = null;

    private String permissions = null;

    public String authorize(){
        userOnline = userEJBBean.findUserByLogin(login, password);
        if (userOnline != null){
            permissions = userOnline.getRoles().get(0).getTitle();
            return "index";
        } else return "logon";
    }

    public String logout(){
        userOnline = null;
        permissions = null;
        return "logon";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserOnline() {
        return userOnline;
    }

    public String getPermissions() {
        return permissions;
    }
}
