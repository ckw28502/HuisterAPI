package nl.fontys.s3.huister.business.request.order;

import lombok.*;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateOrderRequest {
    @Setter
    private long id;
    private OrderStatus status;
}
