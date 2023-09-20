package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.property.CreatePropertyUseCase;
import nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatePropertyUseCaseImpl implements CreatePropertyUseCase {
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;

    @Override
    public void createProperty(CreatePropertyRequest request) {
        int cityId;
        String cityName=request.getCityName();

        //Check if city already in repository
        if (cityRepository.cityNameExists(cityName)){
            cityId=cityRepository.getCityByName(cityName).get().getId();
        }else {
            cityId=cityRepository.createCity(cityName);
        }

        request.setCityId(cityId);
        propertyRepository.createProperty(request);
    }
}
