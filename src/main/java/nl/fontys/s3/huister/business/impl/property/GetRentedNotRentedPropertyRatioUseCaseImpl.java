package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.InvalidRoleException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.GetRentedNotRentedPropertyRatioUseCase;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetRentedNotRentedPropertyRatioUseCaseImpl implements GetRentedNotRentedPropertyRatioUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @return number of rented property and number of not rented property
     *
     * @should throw UserNotFoundException when user is not found
     * @should throw InvalidRoleException when user role is customer
     * @should return the correct response when user role is either admin or owner
     */
    @Override
    public GetRentedNotRentedPropertyRatioResponse getRentedNotRentedPropertyRatio() {
        Optional<UserEntity>optionalUser=userRepository.findById(requestAccessToken.getId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();

        int rentedCount=0;
        int notRentedCount=0;

        switch (user.getRole()){
            case ADMIN -> {
                rentedCount=propertyRepository.countByEndRentIsNotNull();
                notRentedCount=propertyRepository.countByEndRentIsNull();
            }
            case OWNER -> {
                rentedCount=propertyRepository.countByEndRentIsNotNullAndOwner(user);
                notRentedCount=propertyRepository.countByEndRentIsNullAndOwner(user);
            }
            case CUSTOMER ->throw new InvalidRoleException();
        }
        return GetRentedNotRentedPropertyRatioResponse.builder()
                .rented(rentedCount)
                .notRented(notRentedCount)
                .build();
    }
}
