package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.domain.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public void updateOrder(UpdateOrderRequest request) {
        if (!orderRepository.doesOrderExists(request.getId())){
            throw new OrderNotFoundException();
        }
        orderRepository.updateOrder(request);
    }
}
