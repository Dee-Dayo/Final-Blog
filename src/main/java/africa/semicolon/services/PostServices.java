package africa.semicolon.services;

import africa.semicolon.data.models.Post;

public interface PostServices {
    void addPost(Post post);

    Long countNoOfPosts();

    void deletePost(Post post);

    Post findPostById(String postId);
}
