package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.module.Module;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "CODELAB")
public class Codelab {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "MODULE_ID")
    private Module module;

    public Codelab() {
    }

    public Codelab(String name, String description, Module module) {
        this.name = name;
        this.description = description;
        this.module = module;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }

    public String getDescription() {
        return description;
    }
}
