package africa.semicolon.dto.requests;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String author;
    private String title;
    private String content;
}
