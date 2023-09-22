package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.domain.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.model.Order;

import java.util.List;

public interface OrderRepository {
    void createOrder(CreateOrderRequest request);
    void updateOrder(UpdateOrderRequest request);
    List<Order> getAllOrder(int userId);
    boolean doesOrderExists(int id);
}
