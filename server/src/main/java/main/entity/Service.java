package main.entity;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "cost_our", precision = 2, scale = 18, nullable = false)
    private long costOur;

    @Column(name = "cost_foreign", precision = 2, scale = 18, nullable = false)
    private long costForeign;

    public Service() {
    }

    public Service(String name, long costOur, long costForeign) {
        this.name = name;
        this.costOur = costOur;
        this.costForeign = costForeign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCostOur() {
        return costOur;
    }

    public void setCostOur(long costOur) {
        this.costOur = costOur;
    }

    public long getCostForeign() {
        return costForeign;
    }

    public void setCostForeign(long costForeign) {
        this.costForeign = costForeign;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", costOur=" + costOur +
                ", costForeign=" + costForeign +
                '}';
    }
}
