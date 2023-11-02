package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.CreatePropertyUseCase;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePropertyUseCaseImpl implements CreatePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param request new Property request from client
     *
     * @should create new City object when cityName doesn't exist in CityRepository then create the property
     * @should add the existing cityId to request before creating new Property
     */
    @Override
    public void createProperty(CreatePropertyRequest request) {
        Optional<UserEntity>optionalUser=userRepository.findById(request.getOwnerId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        propertyRepository.saveProperty(
                request.getOwnerId(),
                request.getCityName(),
                request.getStreetName(),
                request.getPostCode(),
                request.getDescription(),
                request.getImageUrl(),
                request.getArea(),
                request.getPrice()
        );
    }
}
