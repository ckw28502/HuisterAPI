package nl.fontys.s3.huister.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private int id;
    private UserRole role;
    private String username,password,name,phoneNumber,profilePictureUrl;
    private boolean activated;
}
