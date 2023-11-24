package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.CreatePropertyUseCase;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePropertyUseCaseImpl implements CreatePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @param request new Property request
     *
     * @should throw UserNotFoundException when user is not found
     * @should create property
     */
    @Override
    public void createProperty(CreatePropertyRequest request) {
        Optional<UserEntity>optionalUser=userRepository.findById(requestAccessToken.getId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        propertyRepository.saveProperty(
                optionalUser.get().getId(),
                request.getCityName(),
                request.getStreetName(),
                request.getPostCode(),
                request.getDescription(),
                request.getImageUrl(),
                request.getArea(),
                request.getPrice(),
                request.getHouseNumber()
        );
    }
}
