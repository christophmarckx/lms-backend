package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.module.Module;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "COURSE_MODULE",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "MODULE_ID"))
    private List<Module> modules = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String description, List<Module> modules) {
        this.name = name;
        this.description = description;
        this.modules = modules;
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

    public List<Module> getModules() {
        return modules;
    }

    public void updateCourseName(String newName) {
        this.name = newName;
    }
}
