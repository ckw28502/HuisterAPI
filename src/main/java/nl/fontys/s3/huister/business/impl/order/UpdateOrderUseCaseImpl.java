package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {
    private final OrderRepository orderRepository;
    private final PropertyRepository propertyRepository;

    /**
     *
     * @param request contains new status for order and order id
     *
     * @should throw an OrderNotFoundException when order is not found
     * @should update the order when order is found
     * @should change property status to rented if the new order status is accepted
     */
    @Override
    public void updateOrder(UpdateOrderRequest request) {
        if (!orderRepository.doesOrderExists(request.getId())){
            throw new OrderNotFoundException();
        }
        OrderEntity order =orderRepository.updateOrder(request);

        if (request.getStatus()== OrderStatus.ACCEPTED){
            propertyRepository.rentProperty(order);
        }
    }
}
