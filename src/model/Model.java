package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the model database table.
 * 
 */
@Entity
@NamedQuery(name="Model.findAll", query="SELECT m FROM Model m")
public class Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String title;

	//bi-directional many-to-one association to Diler
	@ManyToOne
	@JoinColumn(name="diler_id")
	private Diler diler;

	//bi-directional many-to-one association to Carforsupply
	@OneToMany(mappedBy="model")
	private List<Carforsupply> carforsupplies;

	public Model() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Diler getDiler() {
		return this.diler;
	}

	public void setDiler(Diler diler) {
		this.diler = diler;
	}

	public List<Carforsupply> getCarforsupplies() {
		return this.carforsupplies;
	}

	public void setCarforsupplies(List<Carforsupply> carforsupplies) {
		this.carforsupplies = carforsupplies;
	}

}