package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.request.order.CreateOrderRequest;

public interface OrderRepository {
    void createOrder(CreateOrderRequest request);
}
