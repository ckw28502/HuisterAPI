package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @Setter
    private int id;
    private String newPassword;
}
