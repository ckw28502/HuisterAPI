package nl.fontys.s3.huister.business.response.user;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
