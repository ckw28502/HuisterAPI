package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginRequest {
    private String username;
    private String password;
}
