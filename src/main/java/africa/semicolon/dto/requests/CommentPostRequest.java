package africa.semicolon.dto.requests;

import africa.semicolon.data.models.User;
import lombok.Data;

@Data
public class CommentPostRequest {
    private String postId;
    private User commenter;
    private String comment;
}
