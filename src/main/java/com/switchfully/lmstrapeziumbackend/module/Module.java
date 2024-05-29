package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "MODULE")
public class Module {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Module parentModule;

    @ManyToMany(mappedBy = "modules")
    List<Course> courses;


    public Module() {}

    public Module(String name, Module parentModule) {
        this.name = name;
        this.parentModule = parentModule;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
