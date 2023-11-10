package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.price.PriceMustBeMoreThanZeroException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    /**
     *
     * @param request contains new order request from client
     *
     * @should throw UserNotFoundException when owner or customer is not in the repository
     * @should throw PropertyNotFoundException when property is not found in the repository
     * @should throw PriceMustBeMoreThanZeroException when price is equals or below zero
     * @should create new order when request is valid
     */
    @Override
    public void createOrder(CreateOrderRequest request) {
        UserEntity owner=getUser(request.getOwnerId());
        UserEntity customer=getUser(request.getCustomerId());

        Optional<PropertyEntity>optionalProperty=propertyRepository.findById(request.getPropertyId());
        if (optionalProperty.isEmpty()) {
            throw new PropertyNotFoundException();
        }

        if (request.getPrice()<=0){
            throw new PriceMustBeMoreThanZeroException();
        }


        OrderEntity order=OrderEntity.builder()
                .property(optionalProperty.get())
                .owner(owner)
                .status(OrderStatus.CREATED)
                .duration(request.getDuration())
                .customer(customer)
                .price(request.getPrice())
                .build();

        orderRepository.save(order);
    }

    private UserEntity getUser(long userId) {
        Optional<UserEntity>user=userRepository.findById(userId);
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        return user.get();
    }
}
