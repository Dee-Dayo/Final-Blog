package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
import africa.semicolon.data.models.Post;
import africa.semicolon.data.repositories.CommentRepository;
import africa.semicolon.dto.requests.CommentPostRequest;
import africa.semicolon.dto.requests.DeleteCommentRequest;
import africa.semicolon.exceptions.CommentNotFoundException;
import africa.semicolon.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.utils.Mapper.requestMap;

@Service
public class CommentServicesImpl implements CommentServices{
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment saveComment(CommentPostRequest commentPostRequest) {
        Comment comment = requestMap(commentPostRequest);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public long countNoOfViews() {
        return commentRepository.count();
    }

    @Override
    public Comment findCommentById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) throw new CommentNotFoundException("Comment not found");
        return comment.get();
    }

    @Override
    public Comment removeComment(DeleteCommentRequest deleteCommentRequest) {
        Comment comment = findCommentById(deleteCommentRequest.getCommentId());
        commentRepository.delete(comment);
        return comment;
    }
}
