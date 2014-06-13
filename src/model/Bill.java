package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the bill database table.
 * 
 */
@Entity
@NamedQuery(name="Bill.findAll", query="SELECT b FROM Bill b")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

    public Bill(int count, Date date){
        this.count = count;
        this.date = date;
    }

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int count;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	//bi-directional one-to-one association to User
	@OneToOne(mappedBy="bill")
	private User user;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="bill")
	private List<History> histories;

	public Bill() {
	}

    public Bill(int count, Timestamp date, User user){
        this.count = count;
        this.date = date;
        this.user = user;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<History> getHistories() {
		return this.histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}
}