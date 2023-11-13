package nl.fontys.s3.huister.business.request.user;

import lombok.*;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String profilePictureUrl;

    private UserRole role;
}