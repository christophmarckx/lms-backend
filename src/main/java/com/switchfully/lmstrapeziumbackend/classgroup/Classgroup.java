package com.switchfully.lmstrapeziumbackend.classgroup;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "CLASSGROUP")
public class Classgroup {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    public Classgroup() {}

    public Classgroup(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
