package main.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "services")
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

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
}
