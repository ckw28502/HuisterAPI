package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetAllOrdersUseCaseImpl implements GetAllOrdersUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param userId current logged in user's id
     * @return list of orders connected to current logged in user
     *
     * @should throw an UserNotFoundException when user is not found
     * @should return list of orders when user is found
     */
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
