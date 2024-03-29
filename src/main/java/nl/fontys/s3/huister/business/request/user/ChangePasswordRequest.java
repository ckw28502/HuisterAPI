package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ChangePasswordRequest {
    private String username;
    private String newPassword;
}
