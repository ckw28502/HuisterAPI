package nl.fontys.s3.huister.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    private int id,ownerId,customerId,propertyId,duration;
    private double price;
    private OrderStatus status;
}
