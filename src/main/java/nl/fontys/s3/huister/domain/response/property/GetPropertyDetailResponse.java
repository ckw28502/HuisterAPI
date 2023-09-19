package nl.fontys.s3.huister.domain.response.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPropertyDetailResponse {
    private int id,ownerId,price;
    private String streetName,postCode,description,ownerName,cityName;
    private List<String> imageUrls;
    private long area;
}
