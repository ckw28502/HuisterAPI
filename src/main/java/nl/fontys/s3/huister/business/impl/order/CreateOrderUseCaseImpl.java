package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.price.PriceMustBeMoreThanZeroException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.domain.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public void createOrder(CreateOrderRequest request) {
        checkUserExist(request.getOwnerId());
        checkUserExist(request.getCustomerId());

        if (propertyRepository.getPropertyById(request.getPropertyId()).isEmpty()){
            throw new PropertyNotFoundException();
        }

        if (request.getPrice()<=0){
            throw new PriceMustBeMoreThanZeroException();
        }

        orderRepository.createOrder(request);
    }

    private void checkUserExist(int userId) {
        if (userRepository.getUserById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
    }
}
