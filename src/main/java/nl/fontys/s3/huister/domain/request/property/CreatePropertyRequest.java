package nl.fontys.s3.huister.domain.request.property;

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
    private int ownerId,cityId,price;
    private String streetName,postCode,description;
    private List<String> imageUrls;
    private long area;
}
