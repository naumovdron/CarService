package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "works")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "date_work", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne
    @Column(name = "car_id")
    private Car car;

    @ManyToOne
    @Column(name = "service_id")
    private Service service;

    public Work() {
    }

    public Work(Date date, Master master, Car car, Service service) {
        this.date = date;
        this.master = master;
        this.car = car;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", dateWork=" + date +
                ", masterId=" + master +
                ", carId=" + car +
                ", serviceId=" + service +
                '}';
    }
}
