package nl.fontys.s3.huister.business.response.user;

import lombok.*;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class LoginResponse {
    private long id;
    private String name;
    private UserRole role;
    private String profilePictureUrl;
}
