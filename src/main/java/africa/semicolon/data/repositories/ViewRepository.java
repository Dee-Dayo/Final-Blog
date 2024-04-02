package africa.semicolon.data.repositories;

import africa.semicolon.data.models.View;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ViewRepository extends MongoRepository<View, String> {
}
