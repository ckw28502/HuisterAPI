package nl.fontys.s3.huister.business.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOwnersResponse {
    private int id,propertyOwned,propertyRented;
    private String name,email;
}
