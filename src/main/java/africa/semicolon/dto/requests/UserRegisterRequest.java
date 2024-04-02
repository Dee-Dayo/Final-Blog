package africa.semicolon.dto.requests;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
