package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {
    private final OrderRepository orderRepository;

    /**
     *
     * @param request contains new status for order and order id
     *
     * @should throw an OrderNotFoundException when order is not found
     * @should update the order when order is found
     */
    @Override
    public void updateOrder(UpdateOrderRequest request) {
        if (!orderRepository.doesOrderExists(request.getId())){
            throw new OrderNotFoundException();
        }
        orderRepository.updateOrder(request);
    }
}
