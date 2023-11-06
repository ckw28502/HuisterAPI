package nl.fontys.s3.huister.business.response.property;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class GetAllPropertiesResponse {
    private long id;
    private long cityId;

    private String streetName;
    private String postCode;
    private String description;
    private String ownerName;
    private String cityName;
    private String imageUrl;

    private double area,price;
}
