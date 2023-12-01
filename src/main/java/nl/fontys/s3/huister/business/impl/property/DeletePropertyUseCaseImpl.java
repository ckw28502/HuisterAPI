package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.DeletePropertyUseCase;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletePropertyUseCaseImpl implements DeletePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @param id id of the property which will be deleted
     *
     * @should throw PropertyNotFoundException if id is invalid
     * @should throw UnauthorizedUserException when user is unauthorized
     * @should delete property
     */
    @Override
    public void deleteProperty(long id) {
        Optional<PropertyEntity> propertyOptional=propertyRepository.findByIdAndIsDeletedIsNull(id);
        if (propertyOptional.isEmpty()){
            throw new PropertyNotFoundException();
        }

        if (propertyOptional.get().getOwner().getId()!=requestAccessToken.getId()){
            throw new UnauthorizedUserException();
        }

        propertyRepository.deleteProperty(id);
    }
}
