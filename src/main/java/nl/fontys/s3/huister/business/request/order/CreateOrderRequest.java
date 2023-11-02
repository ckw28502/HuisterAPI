package nl.fontys.s3.huister.business.request.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    private long ownerId;
    private long customerId;
    private long propertyId;
    private int duration;

    private double price;
}
