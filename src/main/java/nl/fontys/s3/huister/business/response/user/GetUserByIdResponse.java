package nl.fontys.s3.huister.business.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Data
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
