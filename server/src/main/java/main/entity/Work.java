package main.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "works")
@Data
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="workGenerator")
    @SequenceGenerator(name="workGenerator", sequenceName="workSeq")
    private long id;

    @Column(name = "date_work", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;

    public Work() {
    }

    public Work(Date date, Master master, Car car, Service service) {
        this.date = date;
        this.master = master;
        this.car = car;
        this.service = service;
    }
}
