package nl.fontys.s3.huister.business.impl.property;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.business.interfaces.property.UpdatePropertyUseCase;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePropertyUseCaseImpl implements UpdatePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @param request property request
     *
     * @should throw new PropertyNotFoundException if property is not found
     * @should throw new UnauthorizedUserException when property owner is not user
     * @should update the chosen property
     */
    @Override
    @Transactional
    public void updateProperty(UpdatePropertyRequest request) {
        Optional<PropertyEntity>optionalProperty=propertyRepository.findByIdAndIsDeletedIsNull(request.getId());
        if (optionalProperty.isEmpty()){
            throw new PropertyNotFoundException();
        }

        PropertyEntity property=optionalProperty.get();

        if (property.getOwner().getId()!=requestAccessToken.getId()){
            throw new UnauthorizedUserException();
        }

        propertyRepository.updateProperty(request.getId(),
                request.getImageUrl(),
                request.getDescription(),
                request.getPrice());
    }
}
