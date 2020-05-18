package main.entity;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean foreign) {
        isForeign = foreign;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", color='" + color + '\'' +
                ", mark='" + mark + '\'' +
                ", isForeign=" + isForeign +
                '}';
    }
}
