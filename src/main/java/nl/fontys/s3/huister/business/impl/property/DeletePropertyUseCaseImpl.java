package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.model.Property;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.DeletePropertyUseCase;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletePropertyUseCaseImpl implements DeletePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;

    /**
     *
     * @param id id of the property which will be deleted
     *
     * @should throw PropertyNotFoundException if id is invalid
     * @should delete property if id is valid
     * @should delete city object if there are no property in the city
     */
    @Override
    public void deleteProperty(int id) {
        Optional<Property> propertyOptional=propertyRepository.getPropertyById(id);
        if (propertyOptional.isEmpty()){
            throw new PropertyNotFoundException();
        }
        Property property=propertyOptional.get();
        int cityId=property.getCityId();
        propertyRepository.deleteProperty(id);
        if (propertyRepository.isCityHasNoProperty(cityId)){
            cityRepository.deleteCity(cityId);
        }
    }
}
