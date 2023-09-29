package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {
    void createOrder(CreateOrderRequest request);
    void updateOrder(UpdateOrderRequest request);
    List<OrderEntity> getAllOrder(int userId);
    boolean doesOrderExists(int id);
}
