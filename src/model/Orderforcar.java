package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the orderforcar database table.
 * 
 */
@Entity
@NamedQuery(name="Orderforcar.findAll", query="SELECT o FROM Orderforcar o")
public class Orderforcar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name="user_id")
	private int userId;

	//bi-directional many-to-one association to Car
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;

    private int cancel;

	public Orderforcar() {
	}

    public Orderforcar(Timestamp date, int userId, Car car, int cancel){
        this.date = date;
        this.userId = userId;
        this.car = car;
        this.cancel = cancel;
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Car getCar() {
		return this.car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }
}