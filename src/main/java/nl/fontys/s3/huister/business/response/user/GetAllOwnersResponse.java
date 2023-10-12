package nl.fontys.s3.huister.business.response.user;

import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class GetAllOwnersResponse {
    private int id,propertyOwned,propertyRented;
    private String name,email,profilePictureUrl;
}
