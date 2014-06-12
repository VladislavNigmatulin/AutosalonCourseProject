package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the autosalonbill database table.
 * 
 */
@Entity
@NamedQuery(name="Autosalonbill.findAll", query="SELECT a FROM Autosalonbill a")
public class Autosalonbill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private int money;

	//bi-directional many-to-one association to Autosalon
	@ManyToOne
	@JoinColumn(name="autosalon_id")
	private Autosalon autosalon;

	public Autosalonbill() {
	}

    public Autosalonbill(Timestamp date, int money, Autosalon autosalon){
        this.date = date;
        this.money = money;
        this.autosalon = autosalon;
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

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Autosalon getAutosalon() {
		return this.autosalon;
	}

	public void setAutosalon(Autosalon autosalon) {
		this.autosalon = autosalon;
	}

}