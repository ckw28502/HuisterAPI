package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ChangePasswordRequest {
    @Setter
    private long id;
    private String newPassword;
}
