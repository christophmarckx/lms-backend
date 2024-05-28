package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.module.Module;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue
    UUID id;

    @Column(name = "NAME")
    String name;

    @Column(name = "DESCRIPTION")
    String description;

    @ManyToMany
    @JoinTable(
            name = "COURSE_MODULE",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "MODULE_ID"))
    private List<Module> modules;

    public Course() {}

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
