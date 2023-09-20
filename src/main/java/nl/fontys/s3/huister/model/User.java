package nl.fontys.s3.huister.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private UserRole role;
    private String username,password,name,phoneNumber,profilePictureUrl;
}
