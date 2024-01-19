package nl.fontys.s3.huister.business.response.order;

import lombok.*;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetAllOrdersResponse {
    private String streetName;
    private String cityName;
    private String imageUrl;
    private String ownerName;
    private String customerName;
    @Setter
    private String endRent;

    private double price;
    private long propertyId;
    private long id;

    private OrderStatus status;
}
