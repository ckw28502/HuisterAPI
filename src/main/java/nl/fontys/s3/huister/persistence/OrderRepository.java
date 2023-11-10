package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {
    void createOrder(CreateOrderRequest request);
    OrderEntity updateOrder(UpdateOrderRequest request);
    List<OrderEntity> getAllOrder(int userId);
    List<OrderEntity>getAllAcceptedOrdersForOwner(int userId);
    boolean doesOrderExists(int id);
}
