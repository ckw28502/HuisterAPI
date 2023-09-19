package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.Model.Property;
import nl.fontys.s3.huister.Model.User;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.property.GetPropertyDetailUseCase;
import nl.fontys.s3.huister.domain.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPropertyDetailUseCaseImpl implements GetPropertyDetailUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @Override
    public GetPropertyDetailResponse getPropertyDetail(int id) {

        //Get property from repository
        Optional<Property>propertyOptional=propertyRepository.getPropertyById(id);

        //Check whether property exists or not
        if(propertyOptional.isEmpty()){
            throw new PropertyNotFoundException();
        }
        Property property=propertyOptional.get();

        //Get owner object and city name
        User owner=userRepository.getUserById(property.getOwnerId()).get();
        String cityName=cityRepository.getCityById(property.getCityId()).get().getName();

        //return response
        return GetPropertyDetailResponse.builder()
                .id(property.getId())
                .price(property.getPrice())
                .description(property.getDescription())
                .imageUrls(property.getImageUrls())
                .ownerId(property.getOwnerId())
                .streetName(property.getStreetName())
                .ownerName(owner.getName())
                .postCode(property.getPostCode())
                .area(property.getArea())
                .cityName(cityName)
                .build();
    }
}
