package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.User;
import africa.semicolon.data.models.View;
import africa.semicolon.data.repositories.PostRepository;
import africa.semicolon.dto.requests.CommentPostRequest;
import africa.semicolon.dto.requests.DeleteCommentRequest;
import africa.semicolon.dto.requests.ViewPostRequest;
import africa.semicolon.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public void addView(ViewPostRequest viewPostRequest) {
        Post post = findPostById(viewPostRequest.getPostId());
        View view = viewServices.saveView(viewPostRequest);
        post.getViews().add(view);
        postRepository.save(post);
    }

    @Override
    public void addComment(CommentPostRequest commentPostRequest) {
        Post post = findPostById(commentPostRequest.getPostId());
        Comment comment = commentServices.saveComment(commentPostRequest);
        post.getComments().add(comment);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Post post = findPostById(deleteCommentRequest.getPostId());
        Comment comment = commentServices.removeComment(deleteCommentRequest);
        post.getComments().remove(comment);
        postRepository.save(post);
    }
}
