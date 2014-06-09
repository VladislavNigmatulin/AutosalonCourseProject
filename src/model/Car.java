package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the car database table.
 * 
 */
@Entity
@NamedQuery(name="Car.findAll", query="SELECT c FROM Car c")
public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String color;

	private int cost;

	private int count;

	@Column(name="model_id")
	private int modelId;

	//bi-directional many-to-one association to Autosalon
	@ManyToOne
	@JoinColumn(name="autosalon_id")
	private Autosalon autosalon;

	//bi-directional many-to-one association to Orderforcar
	@OneToMany(mappedBy="car")
	private List<Orderforcar> orderforcars;

	public Car() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getModelId() {
		return this.modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public Autosalon getAutosalon() {
		return this.autosalon;
	}

	public void setAutosalon(Autosalon autosalon) {
		this.autosalon = autosalon;
	}

	public List<Orderforcar> getOrderforcars() {
		return this.orderforcars;
	}

	public void setOrderforcars(List<Orderforcar> orderforcars) {
		this.orderforcars = orderforcars;
	}

	public Orderforcar addOrderforcar(Orderforcar orderforcar) {
		getOrderforcars().add(orderforcar);
		orderforcar.setCar(this);

		return orderforcar;
	}

	public Orderforcar removeOrderforcar(Orderforcar orderforcar) {
		getOrderforcars().remove(orderforcar);
		orderforcar.setCar(null);

		return orderforcar;
	}

}