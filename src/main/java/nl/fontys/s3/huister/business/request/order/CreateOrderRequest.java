package nl.fontys.s3.huister.business.request.order;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateOrderRequest {
    private long ownerId;
    private long propertyId;
    private int duration;

    private double price;
}
