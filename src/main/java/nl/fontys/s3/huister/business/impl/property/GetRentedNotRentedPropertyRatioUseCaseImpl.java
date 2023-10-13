package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.InvalidRoleException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.GetRentedNotRentedPropertyRatioUseCase;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetRentedNotRentedPropertyRatioUseCaseImpl implements GetRentedNotRentedPropertyRatioUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param userId current user id
     * @return number of rented property and number of not rented property
     *
     * @should throw UserNotFoundException when user is not found
     * @should throw InvalidRoleException when user role is customer
     * @should return the correct response when user role is either admin or owner
     */
    @Override
    public GetRentedNotRentedPropertyRatioResponse getRentedNotRentedPropertyRatio(int userId) {
        Optional<UserEntity>optionalUser=userRepository.getUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();

        List<PropertyEntity>properties=switch (user.getRole()){
            case ADMIN -> propertyRepository.getAllProperties();
            case OWNER -> propertyRepository.getPropertiesByOwner(userId);
            case CUSTOMER ->throw new InvalidRoleException();
        };
        long rentedPropertiesCount=properties.stream().filter(PropertyEntity::isRented).count();
        long notRentedPropertiesCount=properties.stream().filter(property->!property.isRented()).count();
        return GetRentedNotRentedPropertyRatioResponse.builder()
                .rented(rentedPropertiesCount)
                .notRented(notRentedPropertiesCount)
                .build();
    }
}
