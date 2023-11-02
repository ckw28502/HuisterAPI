package nl.fontys.s3.huister.business.request.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePropertyRequest {
    private long ownerId;
    private long cityId;

    private String streetName;
    private String postCode;
    private String description;
    private String cityName;

    private String imageUrl;

    private double area,price;
}
