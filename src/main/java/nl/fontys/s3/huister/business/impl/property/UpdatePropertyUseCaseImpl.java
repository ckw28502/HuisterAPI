package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.UpdatePropertyUseCase;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdatePropertyUseCaseImpl implements UpdatePropertyUseCase {
    private final PropertyRepository propertyRepository;

    /**
     *
     * @param request new property data from client
     *
     * @should throw new PropertyNotFoundException if property is not found in repository
     * @should update the chosen property
     */
    @Override
    public void updateProperty(UpdatePropertyRequest request) {
        if (propertyRepository.getPropertyById(request.getId()).isEmpty()){
            throw new PropertyNotFoundException();
        }
        propertyRepository.updateProperty(request);
    }
}
