package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany
    @JoinTable(
            name = "CLASSGROUP_APP_USER",
            joinColumns = @JoinColumn(name = "APP_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLASSGROUP_ID"))
    private List<Classgroup> classgroups;

    public User() {}

    public User(String email, String displayName, UserRole role) {
        this.email = email;
        this.displayName = displayName;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserRole getRole() {
        return role;
    }

    public void addClassgroup(Classgroup classgroup) {
        if (classgroups.contains(classgroup)) {
            throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION");
        }
        classgroups.add(classgroup);
    }
}
