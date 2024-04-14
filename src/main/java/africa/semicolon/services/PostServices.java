package africa.semicolon.services;

import africa.semicolon.data.models.Post;
import africa.semicolon.dto.requests.CommentPostRequest;
import africa.semicolon.dto.requests.DeleteCommentRequest;
import africa.semicolon.dto.requests.ViewPostRequest;
import africa.semicolon.dto.responses.CommentPostResponse;
import africa.semicolon.dto.responses.ViewPostResponse;

public interface PostServices {
    void addPost(Post post);
    Long countNoOfPosts();
    void deletePost(Post post);
    Post findPostById(String postId);
    ViewPostResponse addView(ViewPostRequest viewPostRequest);
    CommentPostResponse addComment(CommentPostRequest commentPostRequest);
    CommentPostResponse deleteComment(DeleteCommentRequest deleteCommentRequest);
}
