package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    String token;
}
