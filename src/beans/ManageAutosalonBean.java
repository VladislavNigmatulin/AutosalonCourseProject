package beans;

import ejb.AdminEJBBeanLocal;
import model.Autosalon;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Named
@RequestScoped
public class ManageAutosalonBean implements Serializable {

    @EJB
    private AdminEJBBeanLocal adminEJBBean;

    @Size(min = 2, max = 30, message = "Страна должна быть от 2 до 30 символов")
    private String country;

    @Size(min = 2, max = 30, message = "Город должен быть от 2 до 30 символов")
    private String city;

    @Size(min = 2, max = 40, message = "Адрес должен быть от 2 до 40 символов")
    private String address;

    @Size(min = 6, max = 11, message = "Телефон должен быть от 6 до 11 символов")
    private String phone;

    @Size(min = 5, max = 40, message = "EMail должен быть от 5 до 40 символов")
    private String email;

    private int money;

    public String createNewAutosalons(){
        Autosalon autosalon = new Autosalon(country, city, address, email, phone);
        adminEJBBean.createNewAutosalon(autosalon, money);
        return "showAllAutosalons";
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
