package nl.fontys.s3.huister.business.interfaces.order;

import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;


public interface CreateOrderUseCase {
    void createOrder(CreateOrderRequest request);
}
