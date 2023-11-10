package nl.fontys.s3.huister.business.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginRequest {
    private String username;
    private String password;
}
