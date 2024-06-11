package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CommentDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCommentDTO;
import com.switchfully.lmstrapeziumbackend.comment.Comment;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.student.StudentMapper;

public class CommentMapper {
    public static Comment toComment(CreateCommentDTO createCommentDTO, Codelab codelab, User user){
        return new Comment(codelab, user, createCommentDTO.comment());
    }

    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getCodelab().getId(),
                comment.getComment(),
                StudentMapper.toDTO(comment.getStudent())
        );
    }
}
