package nl.fontys.s3.huister.business.response.user;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    String token;
}
