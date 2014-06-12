package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the trade database table.
 * 
 */
@Entity
@NamedQuery(name="Trade.findAll", query="SELECT t FROM Trade t")
public class Trade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int count;

	//bi-directional many-to-one association to Supply
	@ManyToOne
	@JoinColumn(name="supply_id")
	private Supply supply;

	//bi-directional many-to-one association to Carforsupply
	@ManyToOne
	@JoinColumn(name="carForSupply_id")
	private Carforsupply carforsupply;

	public Trade() {
	}

    public Trade(int count, Supply supply, Carforsupply carforsupply){
        this.count = count;
        this.supply = supply;
        this.carforsupply = carforsupply;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Supply getSupply() {
		return this.supply;
	}

	public void setSupply(Supply supply) {
		this.supply = supply;
	}

	public Carforsupply getCarforsupply() {
		return this.carforsupply;
	}

	public void setCarforsupply(Carforsupply carforsupply) {
		this.carforsupply = carforsupply;
	}

}