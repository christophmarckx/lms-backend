package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CLASSGROUP_APP_USER",
            joinColumns = @JoinColumn(name = "CLASSGROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "APP_USER_ID"))
    private List<User> users = new ArrayList<>();

    public Classgroup() {
    }

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

    public void addUser(User userToAdd) {
        if (userToAdd.getRole() != UserRole.COACH && userToAdd.getClassgroups().size() == 1){
            userToAdd.getClassgroups().getFirst().getUsers().remove(userToAdd); //send help
            users.add(userToAdd);

        } else if (!users.contains(userToAdd)) {
            users.add(userToAdd);
        }
    }

    @Override
    public String toString() {
        return "Classgroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
