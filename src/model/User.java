package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int age;

	private String login;

	private String name;

	private String password;

	private String patronymic;

	private String surname;

    public User(String login, String password, String surname, String name, String patronymic, int age){
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
    }

	//bi-directional one-to-one association to Bill
	@OneToOne
	@JoinColumn(name="bill_id")
	private Bill bill;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="users")
	private List<Role> roles;

    private int autosalon_id;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPatronymic() {
		return this.patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public int getAutosalon_id() {
        return autosalon_id;
    }

    public void setAutosalon_id(int autosalon_id) {
        this.autosalon_id = autosalon_id;
    }
}