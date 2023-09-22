package nl.fontys.s3.huister.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private int id,ownerId,customerId,propertyId,duration;
    private long price;
    private OrderStatus status;
}
