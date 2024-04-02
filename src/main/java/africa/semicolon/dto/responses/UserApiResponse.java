package africa.semicolon.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserApiResponse {
    boolean isSuccessful;
    Object userRegisterResponse;
}
