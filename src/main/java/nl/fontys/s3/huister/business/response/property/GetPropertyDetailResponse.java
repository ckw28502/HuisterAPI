package nl.fontys.s3.huister.business.response.property;

import lombok.*;


@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@AllArgsConstructor
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
