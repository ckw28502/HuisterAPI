package nl.fontys.s3.huister.business.request.user;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class ActivateAccountRequest {
    private String username;
}
