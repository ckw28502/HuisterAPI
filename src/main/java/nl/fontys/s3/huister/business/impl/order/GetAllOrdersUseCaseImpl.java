package nl.fontys.s3.huister.business.impl.order;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.city.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetAllOrdersUseCaseImpl implements GetAllOrdersUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;

    /**
     *
     * @param userId current logged in user's id
     * @return list of orders connected to current logged in user
     *
     * @should throw an UserNotFoundException when user is not found
     * @should throw a PropertyNotFoundException when property is not found
     * @should throw a CityNotFoundException when city is not found
     * @should return list of orders when user is found
     */
    @Override
    public List<GetAllOrdersResponse> getAllOrders(int userId) {
        if (userRepository.getUserById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        List<OrderEntity> orders=orderRepository.getAllOrder(userId);
        return orders.stream().map(order -> {
            Optional<PropertyEntity>optionalProperty=propertyRepository.getPropertyById(order.getPropertyId());
            if (optionalProperty.isEmpty()){
                throw new PropertyNotFoundException();
            }
            PropertyEntity property=optionalProperty.get();

            Optional<CityEntity>optionalCity=cityRepository.getCityById(property.getCityId());
            if (optionalCity.isEmpty()){
                throw new CityNotFoundException();
            }
            CityEntity city=optionalCity.get();

            return GetAllOrdersResponse.builder()
                    .cityName(city.getName())
                    .price(order.getPrice())
                    .imageUrl(property.getImageUrls().get(0))
                    .streetName(property.getStreetName())
                    .endRent(property.getEndRent().toString())
                    .status(order.getStatus())
                    .build();
        }).toList();
    }
}
