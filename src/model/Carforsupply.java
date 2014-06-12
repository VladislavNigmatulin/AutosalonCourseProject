package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the carforsupply database table.
 * 
 */
@Entity
@NamedQuery(name="Carforsupply.findAll", query="SELECT c FROM Carforsupply c")
public class Carforsupply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String color;

	private int cost;

	private int count;

	//bi-directional many-to-one association to Model
	@ManyToOne
	@JoinColumn(name="model_id")
	private Model model;

	//bi-directional many-to-one association to Trade
	@OneToMany(mappedBy="carforsupply")
	private List<Trade> trades;

	public Carforsupply() {
	}

    public Carforsupply(int id, String color, int cost, int count, Model model, List<Trade> trades){
        this.id = id;
        this.color = color;
        this.cost = cost;
        this.count = count;
        this.model = model;
        this.trades = trades;
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

	public Model getModel() {
		return this.model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public List<Trade> getTrades() {
		return this.trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

}