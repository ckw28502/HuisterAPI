package nl.fontys.s3.huister.domain.response.property;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class GetAllPropertiesResponse {
    private int id;
    private String streetName,postCode,description,ownerName,cityName,imageUrl;
    private long area,price;
}
