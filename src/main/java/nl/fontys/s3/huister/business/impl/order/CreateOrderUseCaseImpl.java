package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.Converter;
import nl.fontys.s3.huister.business.exception.price.PriceMustBeMoreThanZeroException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final AccessToken requestAccessToken;
    private final SimpMessagingTemplate messagingTemplate;
    private final Converter converter;

    /**
     *
     * @param request contains new order request from client
     *
     * @should throw UserNotFoundException when owner is not in the repository
     * @should throw UserNotFoundException when customer is not in the repository
     * @should throw PropertyNotFoundException when property is not found in the repository
     * @should throw PriceMustBeMoreThanZeroException when price is equals or below zero
     * @should create new order when request is valid
     */
    @Override
    public void createOrder(CreateOrderRequest request) {
        UserEntity customer=getUser(requestAccessToken.getId());

        Optional<PropertyEntity>optionalProperty=propertyRepository.findByIdAndIsDeletedIsNull(request.getPropertyId());
        if (optionalProperty.isEmpty()) {
            throw new PropertyNotFoundException();
        }

        if (request.getPrice()<=0){
            throw new PriceMustBeMoreThanZeroException();
        }


        OrderEntity order=OrderEntity.builder()
                .property(optionalProperty.get())
                .owner(optionalProperty.get().getOwner())
                .status(OrderStatus.CREATED)
                .duration(request.getDuration())
                .customer(customer)
                .price(request.getPrice())
                .build();

        OrderEntity newOrder=orderRepository.save(order);
        messagingTemplate.convertAndSend(
                "/notifications/order/"+order.getOwner().getId(),
                converter.orderToResponse(newOrder)
        );
    }

    private UserEntity getUser(long userId) {
        Optional<UserEntity>user=userRepository.findById(userId);
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user.get();
    }
}
