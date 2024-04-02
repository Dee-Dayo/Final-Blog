package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
import africa.semicolon.data.repositories.CommentRepository;
import africa.semicolon.dto.requests.CommentPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
