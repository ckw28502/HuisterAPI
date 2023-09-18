package nl.fontys.s3.huister.domain.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByIdResponse {
    private int id,role;
    private String name,phoneNumber,username,profilePictureUrl;
}
