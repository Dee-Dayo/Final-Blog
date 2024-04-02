package africa.semicolon.services;

import africa.semicolon.data.models.Post;
import africa.semicolon.data.repositories.PostRepository;
import africa.semicolon.exceptions.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServicesImpl implements PostServices{

    @Autowired
    PostRepository postRepository;

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
}
