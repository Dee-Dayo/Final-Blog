package africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Comments")
public class Comment {
    @Id
    private String id;
    @DBRef
    private User commenter;
    private String comment;
    private LocalDateTime timeOfComment = LocalDateTime.now();
}
