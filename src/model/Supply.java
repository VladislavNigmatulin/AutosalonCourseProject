package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the supply database table.
 * 
 */
@Entity
@NamedQuery(name="Supply.findAll", query="SELECT s FROM Supply s")
public class Supply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private int summaryCost;

	//bi-directional many-to-one association to Trade
	@OneToMany(mappedBy="supply")
	private List<Trade> trades;

	public Supply() {
	}

    public Supply(Timestamp date, int summaryCost){
        this.date = date;
        this.summaryCost = summaryCost;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getSummaryCost() {
		return this.summaryCost;
	}

	public void setSummaryCost(int summaryCost) {
		this.summaryCost = summaryCost;
	}

	public List<Trade> getTrades() {
		return this.trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

}