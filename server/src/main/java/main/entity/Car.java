package main.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="carGenerator")
    @SequenceGenerator(name="carGenerator", sequenceName="carSeq")
    private long id;

    @Column(name = "num", length = 20, nullable = false)
    private String num;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "mark", length = 20)
    private String mark;

    @Column(name = "is_foreign", nullable = false)
    private boolean isForeign;

    public Car() {
    }

    public Car(String num, String color, String mark, boolean isForeign) {
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.isForeign = isForeign;
    }
}
