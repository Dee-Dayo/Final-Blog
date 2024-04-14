package africa.semicolon.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewPostResponse {
    private String viewerId;
    private String postTitle;
    private String viewerName;
}
