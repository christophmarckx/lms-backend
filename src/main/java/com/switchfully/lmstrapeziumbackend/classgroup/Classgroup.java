package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CLASSGROUP")
public class Classgroup {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @ManyToMany(mappedBy = "classgroups")
    private List<User> users = new ArrayList<>();


    public Classgroup() {}

    public Classgroup(String name, Course course, List<User> users) {
        this.name = name;
        this.course = course;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }

    public List<User> getUsers() {
        return users;
    }
}
