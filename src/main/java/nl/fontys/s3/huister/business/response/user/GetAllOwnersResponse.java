package nl.fontys.s3.huister.business.response.user;

import lombok.*;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class GetAllOwnersResponse {
    private long id;
    private int propertyOwned;
    private int propertyRented;

    private String name;
    private String email;
    private String profilePictureUrl;
}
