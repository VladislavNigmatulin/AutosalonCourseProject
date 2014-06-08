package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@NamedQuery(name="History.findAll", query="SELECT h FROM History h")
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

    public History(String operation, int sum){
        this.operation = operation;
        this.sum = sum;
    }

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String operation;

	private int sum;

	//bi-directional many-to-one association to Bill
	@ManyToOne
	@JoinColumn(name="bill_ID")
	private Bill bill;

	public History() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getSum() {
		return this.sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}