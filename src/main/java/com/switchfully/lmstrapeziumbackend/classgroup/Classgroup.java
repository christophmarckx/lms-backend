package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.course.Course;
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

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

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
