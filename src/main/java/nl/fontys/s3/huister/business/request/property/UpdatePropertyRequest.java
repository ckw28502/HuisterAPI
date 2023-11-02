package nl.fontys.s3.huister.business.request.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePropertyRequest {
    private long id;
    private String description;
    private String imageUrl;
    private double price;
}
