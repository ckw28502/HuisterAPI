package nl.fontys.s3.huister.business.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private int id;
    private String name;
    private UserRole role;
    private String profilePictureUrl;
}
