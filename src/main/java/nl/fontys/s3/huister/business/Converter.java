package nl.fontys.s3.huister.business;

import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Converter {
    public GetAllOrdersResponse orderToResponse(OrderEntity order){
         GetAllOrdersResponse response=GetAllOrdersResponse.builder()
                .cityName(order.getProperty().getCity().getName())
                .price(order.getPrice())
                .imageUrl(order.getProperty().getImageUrl())
                .streetName(order.getProperty().getStreetName())
                .status(order.getStatus())
                .customerName(order.getCustomer().getName())
                .ownerName(order.getOwner().getName())
                .propertyId(order.getProperty().getId())
                .id(order.getId())
                .build();
        if (order.getProperty().getEndRent()!=null){
            response.setEndRent(order.getProperty().getEndRent().toString());
        }
        return response;
    }
}
