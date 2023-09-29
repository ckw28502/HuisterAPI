package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.GetPropertyDetailUseCase;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
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

    /**
     *
     * @param id id of property that is searched
     * @return GetPropertyDetailResponse with searched property data
     *
     * @should throw PropertyNotFoundException when property can't be found
     * @should return appropriate response after a successful validation
     */
    @Override
    public GetPropertyDetailResponse getPropertyDetail(int id) {

        //Get property from repository
        Optional<PropertyEntity>propertyOptional=propertyRepository.getPropertyById(id);

        //Check whether property exists or not
        if(propertyOptional.isEmpty()){
            throw new PropertyNotFoundException();
        }
        PropertyEntity property=propertyOptional.get();

        //Get owner object and city name
        UserEntity owner=userRepository.getUserById(property.getOwnerId()).get();
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
