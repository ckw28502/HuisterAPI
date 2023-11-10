package nl.fontys.s3.huister.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyEntity {
    private int id,ownerId,cityId;
    private String streetName,postCode,description;
    private List<String>imageUrls;
    private double area,price;
    private boolean isRented;
    private LocalDate endRent;
}
