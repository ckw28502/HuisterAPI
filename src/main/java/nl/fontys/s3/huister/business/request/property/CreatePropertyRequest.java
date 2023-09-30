package nl.fontys.s3.huister.business.request.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePropertyRequest {
    private int ownerId,cityId;
    private String streetName,postCode,description,cityName;
    private List<String> imageUrls;
    private long area,price;
}
