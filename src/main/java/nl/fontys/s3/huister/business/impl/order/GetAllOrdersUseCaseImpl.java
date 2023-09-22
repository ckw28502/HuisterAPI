package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.domain.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetAllOrdersUseCaseImpl implements GetAllOrdersUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public GetAllOrdersResponse getAllOrders(int userId) {
        if (userRepository.getUserById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        return GetAllOrdersResponse.builder()
                .orders(orderRepository.getAllOrder(userId))
                .build();
    }
}
