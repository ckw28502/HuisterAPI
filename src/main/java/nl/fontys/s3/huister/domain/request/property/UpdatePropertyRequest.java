package nl.fontys.s3.huister.domain.request.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePropertyRequest {
    private int id;
    private String description;
    private List<String> imageUrls;
    private long price;
}
