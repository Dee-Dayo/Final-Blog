package africa.semicolon.dto.responses;

import lombok.Data;

@Data
public class CommentPostResponse {
    private String commenterId;
    private String postTitle;
    private String commenterName;
}
