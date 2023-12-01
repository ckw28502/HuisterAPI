package nl.fontys.s3.huister.business.request.property;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreatePropertyRequest {
    private String streetName;
    private String postCode;
    private String description;
    private String cityName;

    private String imageUrl;

    private int houseNumber;

    private double area,price;
}
