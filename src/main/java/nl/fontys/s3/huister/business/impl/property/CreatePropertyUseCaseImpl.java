package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.property.CreatePropertyUseCase;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatePropertyUseCaseImpl implements CreatePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;

    /**
     *
     * @param request new Property request from client
     *
     * @should create new City object when cityName doesn't exist in CityRepository then create the property
     * @should add the existing cityId to request before creating new Property
     */
    @Override
    public int createProperty(CreatePropertyRequest request) {
        int cityId;
        String cityName=request.getCityName();

        //Check if city already in repository
        if (cityRepository.cityNameExists(cityName)){
            cityId=cityRepository.getCityByName(cityName).get().getId();
        }else {
            cityId=cityRepository.createCity(cityName);
        }

        request.setCityId(cityId);
        return propertyRepository.createProperty(request);
    }
}
