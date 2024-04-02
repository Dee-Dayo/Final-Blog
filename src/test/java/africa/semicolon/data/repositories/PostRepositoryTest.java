package africa.semicolon.data.repositories;

import africa.semicolon.data.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void createAPost_postCountIsOne(){
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");
        postRepository.save(post);

        assertThat(postRepository.count(), is(1L));
    }
}