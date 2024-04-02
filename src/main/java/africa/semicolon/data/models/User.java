package africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Users")
public class User {
    private boolean isLoggedIn;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @Id
    private String id;
    @DBRef
    private List<Post> posts = new ArrayList<>();
    private LocalDateTime dateCreated = LocalDateTime.now();
}
