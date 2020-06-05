package main.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "masters")
@Data
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="masterGenerator")
    @SequenceGenerator(name="masterGenerator", sequenceName="masterSeq")
    private long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
    }
}
