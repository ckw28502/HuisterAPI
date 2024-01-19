package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.interfaces.property.GetPropertyDetailUseCase;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPropertyDetailUseCaseImpl implements GetPropertyDetailUseCase {
    private final PropertyRepository propertyRepository;

    /**
     *
     * @param id id of property that is searched
     * @return GetPropertyDetailResponse with searched property data
     *
     * @should throw PropertyNotFoundException when property is not found
     * @should return property when property is found
     */
    @Override
    public GetPropertyDetailResponse getPropertyDetail(long id) {

        //Get property from repository
        Optional<PropertyEntity>propertyOptional=propertyRepository.findById(id);

        //Check whether property exists or not
        if(propertyOptional.isEmpty()){
            throw new PropertyNotFoundException();
        }
        PropertyEntity property=propertyOptional.get();

        //return response
        return GetPropertyDetailResponse.builder()
                .id(property.getId())
                .price(property.getPrice())
                .description(property.getDescription())
                .imageUrl(property.getImageUrl())
                .ownerId(property.getOwner().getId())
                .streetName(property.getStreetName())
                .ownerName(property.getOwner().getName())
                .ownerId(property.getOwner().getId())
                .postCode(property.getPostCode())
                .area(property.getArea())
                .cityName(property.getCity().getName())
                .houseNumber(property.getHouseNumber())
                .build();
    }
}
