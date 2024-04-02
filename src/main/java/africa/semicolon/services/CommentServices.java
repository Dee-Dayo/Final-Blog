package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
import africa.semicolon.dto.requests.CommentPostRequest;

public interface CommentServices {
    Comment saveComment(CommentPostRequest commentPostRequest);

    long countNoOfViews();
}
