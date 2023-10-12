package nl.fontys.s3.huister.business.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllOrdersResponse {
    private String streetName,cityName,imageUrl,endRent;
    private double price;
    private OrderStatus status;
}
