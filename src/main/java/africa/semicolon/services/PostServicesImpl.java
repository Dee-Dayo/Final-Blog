package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.View;
import africa.semicolon.data.repositories.PostRepository;
import africa.semicolon.dto.requests.CommentPostRequest;
import africa.semicolon.dto.requests.DeleteCommentRequest;
import africa.semicolon.dto.requests.ViewPostRequest;
import africa.semicolon.dto.responses.CommentPostResponse;
import africa.semicolon.dto.responses.ViewPostResponse;
import africa.semicolon.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.utils.Mapper.commentPostResponseMap;
import static africa.semicolon.utils.Mapper.viewPostResponseMap;

@Service
public class PostServicesImpl implements PostServices{

    @Autowired
    PostRepository postRepository;
    @Autowired
    ViewServices viewServices;
    @Autowired
    CommentServices commentServices;

    @Override
    public void addPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Long countNoOfPosts() {
        return postRepository.count();
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post findPostById(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) throw new PostNotFoundException("Post not found");
        return post.get();
    }

    @Override
    public ViewPostResponse addView(ViewPostRequest viewPostRequest) {
        Post post = findPostById(viewPostRequest.getPostId());
        View view = viewServices.saveView(viewPostRequest);
        post.getViews().add(view);
        Post newPost = postRepository.save(post);
        return viewPostResponseMap(newPost, view);
    }

    @Override
    public CommentPostResponse addComment(CommentPostRequest commentPostRequest) {
        Post post = findPostById(commentPostRequest.getPostId());
        Comment comment = commentServices.saveComment(commentPostRequest);
        post.getComments().add(comment);
        Post newPost = postRepository.save(post);
        return commentPostResponseMap(newPost, comment);
    }

    @Override
    public CommentPostResponse deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Post post = findPostById(deleteCommentRequest.getPostId());
        Comment comment = commentServices.removeComment(deleteCommentRequest);
        post.getComments().remove(comment);
        Post newPost = postRepository.save(post);
        return commentPostResponseMap(newPost, comment);
    }
}
