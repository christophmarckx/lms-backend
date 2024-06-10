package com.switchfully.lmstrapeziumbackend.codelab;
import com.switchfully.lmstrapeziumbackend.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    public List<Comment> findAllByCodelab(Codelab codelab);
}
