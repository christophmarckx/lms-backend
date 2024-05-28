package com.switchfully.lmstrapeziumbackend.progress;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "PROGRESS")
public class Progress {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(name = "CODELAB_ID")
    Codelab codelab;

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID")
    User user;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    CodelabProgress status;

    public Progress() {}

    public Progress(Codelab codelab, User user, CodelabProgress status) {
        this.codelab = codelab;
        this.user = user;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public Codelab getCodelab() {
        return codelab;
    }

    public User getUser() {
        return user;
    }

    public CodelabProgress getStatus() {
        return status;
    }
}