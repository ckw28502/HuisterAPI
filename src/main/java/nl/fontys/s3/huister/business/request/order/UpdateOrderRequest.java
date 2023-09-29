package nl.fontys.s3.huister.business.request.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderRequest {
    private int id;
    private OrderStatus status;
}
