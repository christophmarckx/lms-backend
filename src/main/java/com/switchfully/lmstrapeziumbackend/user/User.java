package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "APP_USER")
public class User {

    @Id
    private UUID id;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Classgroup> classgroups = new ArrayList<>();

    public User() {
    }

    public User(UUID id, String email, String displayName, UserRole role) {
        this.id = id;
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

    public List<Classgroup> getClassgroups() {
        return classgroups;
    }

    public void addClassgroup(Classgroup classgroup) {
        if (getRole() != UserRole.COACH && getClassgroups().size() == 1) {
            classgroups.removeFirst();
            classgroups.add(classgroup);
        } else if (!classgroups.contains(classgroup)) {
            classgroups.add(classgroup);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", role=" + role +
                ", classgroups=" + classgroups +
                '}';
    }
}
