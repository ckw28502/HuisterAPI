package nl.fontys.s3.huister.business.response.order;

import lombok.*;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetAllOrdersResponse {
    private String streetName;
    private String cityName;
    private String imageUrl;
    private String endRent;

    private double price;

    private OrderStatus status;
}
