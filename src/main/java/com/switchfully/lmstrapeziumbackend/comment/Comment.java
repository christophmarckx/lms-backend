package com.switchfully.lmstrapeziumbackend.comment;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "CODELAB_ID")
    private Codelab codelab;

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID")
    private User student;

    @Column(name = "COMMENT")
    private String comment;

    public Comment() {
    }

    public Comment(Codelab codelab, User student, String comment) {
        this.codelab = codelab;
        this.student = student;
        this.comment = comment;
    }

    public UUID getId() {
        return id;
    }

    public Codelab getCodelab() {
        return codelab;
    }

    public User getStudent() {
        return student;
    }

    public String getComment() {
        return comment;
    }
}
