package africa.semicolon.dto.requests;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private String postId;
    private String commentId;
}
