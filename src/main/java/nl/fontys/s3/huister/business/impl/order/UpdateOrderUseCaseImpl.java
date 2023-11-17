package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.business.exception.utilities.InvalidOperationException;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {
    private final OrderRepository orderRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @param request contains new status for order and order id
     *
     * @should throw an OrderNotFoundException when order is not found
     * @should throw an InvalidOperationException when order is already accepted
     * @should throw an InvalidOperationException when new status is created
     * @should throw an UnauthorizedUserException when user does not have authority for the new status
     * @should update the order when order is found
     */
    @Override
    public void updateOrder(UpdateOrderRequest request) {
        Optional<OrderEntity>optionalOrder=orderRepository.findById(request.getId());
        if (optionalOrder.isEmpty()){
            throw new OrderNotFoundException();
        }
        OrderEntity order=optionalOrder.get();

        if (order.getStatus() == OrderStatus.ACCEPTED){
            throw new InvalidOperationException();
        }

        switch (request.getStatus()){

            case CREATED -> throw new InvalidOperationException();
            case REJECTED, ACCEPTED -> {
                if (requestAccessToken.getRole()!= UserRole.OWNER || requestAccessToken.getId()!=order.getOwner().getId()){
                    throw new UnauthorizedUserException();
                }
            }
            case CANCELLED -> {
                if (requestAccessToken.getRole()!= UserRole.CUSTOMER || requestAccessToken.getId()!=order.getCustomer().getId()){
                    throw new UnauthorizedUserException();
                }
            }
        }

        orderRepository.updateOrder(request.getId(),request.getStatus());

    }
}
