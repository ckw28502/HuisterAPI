package nl.fontys.s3.huister.business.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String username,email,password,name,phoneNumber,profilePictureUrl;
    private UserRole role;
}