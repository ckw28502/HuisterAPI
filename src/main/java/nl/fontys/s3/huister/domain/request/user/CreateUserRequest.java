package nl.fontys.s3.huister.domain.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String username,password,name,profilePictureUrl,phoneNumber;
    private int role;
}