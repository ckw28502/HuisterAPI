package nl.fontys.s3.huister.business.response.user;

import lombok.*;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;

@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@AllArgsConstructor
public class GetUserByIdResponse {
    private long id;

    private UserRole role;

    private String name;
    private String phoneNumber;
    private String username;
    private String email;
    private String profilePictureUrl;
}
