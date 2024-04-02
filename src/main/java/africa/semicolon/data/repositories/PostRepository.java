package africa.semicolon.data.repositories;

import africa.semicolon.data.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
