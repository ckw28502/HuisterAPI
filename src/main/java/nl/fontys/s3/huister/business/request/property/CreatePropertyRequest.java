package nl.fontys.s3.huister.business.request.property;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreatePropertyRequest {
    private long cityId;

    private String streetName;
    private String postCode;
    private String description;
    private String cityName;

    private String imageUrl;

    private double area,price;
}
