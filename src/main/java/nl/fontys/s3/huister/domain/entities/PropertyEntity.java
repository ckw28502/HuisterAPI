package nl.fontys.s3.huister.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyEntity {
    private int id,ownerId,cityId;
    private String streetName,postCode,description;
    private List<String>imageUrls;
    private long area,price;
    private boolean isRented;
}