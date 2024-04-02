package africa.semicolon.dto.responses;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private String username;
    private String dateCreated;
    private String id;
}
