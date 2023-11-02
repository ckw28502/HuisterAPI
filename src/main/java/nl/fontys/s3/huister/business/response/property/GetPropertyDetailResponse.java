package nl.fontys.s3.huister.business.response.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPropertyDetailResponse {
    private long id;
    private long ownerId;

    private String streetName;
    private String postCode;
    private String description;
    private String ownerName;
    private String cityName;

    private String imageUrl;

    private double area,price;
}
