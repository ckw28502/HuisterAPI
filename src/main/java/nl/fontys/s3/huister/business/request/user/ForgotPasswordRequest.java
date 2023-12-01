package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ForgotPasswordRequest {
    private String username;
}
