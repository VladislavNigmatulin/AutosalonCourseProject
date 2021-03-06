package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the autosalon database table.
 * 
 */
@Entity
@NamedQuery(name="Autosalon.findAll", query="SELECT a FROM Autosalon a")
public class Autosalon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private String city;

	private String country;

	private String email;

	private String phone;

	//bi-directional many-to-one association to Autosalonbill
	@OneToMany(mappedBy="autosalon")
	private List<Autosalonbill> autosalonbills;

	//bi-directional many-to-one association to Car
	@OneToMany(mappedBy="autosalon")
	private List<Car> cars;

	public Autosalon() {
	}

    public Autosalon(String country, String city, String address, String email, String phone){
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phone = phone;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Autosalonbill> getAutosalonbills() {
		return this.autosalonbills;
	}

	public void setAutosalonbills(List<Autosalonbill> autosalonbills) {
		this.autosalonbills = autosalonbills;
	}

	public List<Car> getCars() {
		return this.cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

}