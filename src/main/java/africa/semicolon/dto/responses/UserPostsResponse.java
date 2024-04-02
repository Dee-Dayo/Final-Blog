package africa.semicolon.dto.responses;

import africa.semicolon.data.models.Post;
import lombok.Data;

import java.util.List;

@Data
public class UserPostsResponse {
    private String username;
    private List<Post> posts;
}
