package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetAllOrdersUseCaseImpl implements GetAllOrdersUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @return list of orders
     *
     * @should throw an UserNotFoundException when user is not found
     * @should return an empty list when there is no appropriate order
     * @should return list of orders when user is found
     */
    @Override
    public List<GetAllOrdersResponse> getAllOrders() {
        Optional<UserEntity>optionalUser=userRepository.findById(requestAccessToken.getId());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        UserEntity user=optionalUser.get();
        List<OrderEntity> orders=orderRepository.findAllByOwnerOrCustomer(user,user);
        return orders.stream().map(order ->GetAllOrdersResponse.builder()
                    .cityName(order.getProperty().getCity().getName())
                    .price(order.getPrice())
                    .imageUrl(order.getProperty().getImageUrl())
                    .streetName(order.getProperty().getStreetName())
                    .endRent(order.getProperty().getEndRent().toString())
                    .status(order.getStatus())
                    .build()
        ).toList();
    }
}
