package africa.semicolon.dto.responses;

import lombok.Data;

@Data
public class CreatePostResponse {
    private String postId;
    private String postTitle;
    private String postContent;
}
