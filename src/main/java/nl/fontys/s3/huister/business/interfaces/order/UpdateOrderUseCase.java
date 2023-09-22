package nl.fontys.s3.huister.business.interfaces.order;

import nl.fontys.s3.huister.domain.request.order.UpdateOrderRequest;

public interface UpdateOrderUseCase {
    void updateOrder(UpdateOrderRequest request);
}
