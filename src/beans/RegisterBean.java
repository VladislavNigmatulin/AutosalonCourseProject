package beans;

import ejb.UserEJBBeanLocal;
import model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
public class RegisterBean implements Serializable {

    @EJB
    private UserEJBBeanLocal userEJBBean;

    @Size(min = 2, max = 30, message = "Логин должен быть от 2 до 30 символов")
    private String login;

    @Size(min = 2, max = 30, message = "Пароль должен быть от 2 до 30 символов")
    private String password;

    @Size(min = 2, max = 30, message = "Пароль должен быть от 2 до 30 символов")
    private String repeatPassword;

    @Size(min = 2, max = 40, message = "Фамилия должна быть от 2 до 40 символов")
    private String surname;

    @Size(min = 2, max = 40, message = "Имя должно быть от 2 до 40 символов")
    private String name;

    @Size(min = 2, max = 40, message = "Отчество должно быть от 2 до 40 символов")
    private String patronymic;

    private Date dateOfBirth;

    public String goToRegister(){
        return "register";
    }

    public String register(){
        int age = 0;
        User user = new User(login, password, surname, name, patronymic, age);
        userEJBBean.registerNewClient(user);
        return "index";
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void validatePassword(ComponentSystemEvent event)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputPassword = (UIInput) components.findComponent("passwordReg");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("repeatPasswordReg");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword))
        {
            FacesMessage msg = new FacesMessage("Пароль и повторение пароля должны совпадать");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();
        }
    }

    public void validateNoExistingOfLogin(ComponentSystemEvent event)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputLogin = (UIInput) components.findComponent("loginReg");
        String login = uiInputLogin.getLocalValue() == null ? ""
                : uiInputLogin.getLocalValue().toString();
        String loginId = uiInputLogin.getClientId();
        if (userEJBBean.checkUserWithSameLogin(login))
        {
            FacesMessage msg = new FacesMessage("Пользователь с таким логином уже зарегистрирован");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(loginId, msg);
            fc.renderResponse();
        }

    }
}
