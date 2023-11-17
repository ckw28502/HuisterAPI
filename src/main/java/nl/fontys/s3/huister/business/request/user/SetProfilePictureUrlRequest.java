package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SetProfilePictureUrlRequest {
    private String username;
    private String profilePictureUrl;
}
