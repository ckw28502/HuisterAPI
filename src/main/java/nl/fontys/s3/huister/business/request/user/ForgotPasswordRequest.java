package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class ForgotPasswordRequest {
    private String username;
}
