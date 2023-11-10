package nl.fontys.s3.huister.business.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetProfilePictureUrlRequest {
    private String username;
    private String profilePictureUrl;
}
