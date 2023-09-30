package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.business.interfaces.property.GetAllPropertiesUseCase;
import nl.fontys.s3.huister.business.exception.city.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllPropertiesUseCaseImpl implements GetAllPropertiesUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    /**
     *
     * @param userId logged in user id
     * @return List of properties data
     *
     * @should throw UserNotFoundException when cannot get user data from userId parameter
     * @should throw CityNotFoundException when cannot get city data from cityId parameter
     * @should return List of responses when all data are valid
     */
    @Override
    public List<GetAllPropertiesResponse> getAllProperties(int userId) {

        //get current logged in user
        UserEntity user=userRepository.getUserById(userId).get();

        //get retrieved data based on user role
        List<PropertyEntity>properties=switch (user.getRole()){
            case ADMIN:
                yield propertyRepository.getAllProperties();
            case OWNER:
                yield propertyRepository.getPropertiesByOwner(userId);
            case CUSTOMER:
                yield propertyRepository.getAllNotRentedProperties();
        };

        //Define list of response
        List<GetAllPropertiesResponse>responses=new ArrayList<>();

        //iterate the property list
        for (PropertyEntity property:properties){

            //Get owner name
            Optional<UserEntity> owner=userRepository.getUserById(property.getOwnerId());
            if (owner.isEmpty()){
                throw new UserNotFoundException();
            }
            String ownerName=owner.get().getName();

            //Get city name
            Optional<CityEntity> city=cityRepository.getCityById(property.getCityId());
            if (city.isEmpty()){
                throw new CityNotFoundException();
            }
            String cityName=city.get().getName();

            //add all current property's data to the response
            GetAllPropertiesResponse response= GetAllPropertiesResponse.builder()
                    .id(property.getId())
                    .area(property.getArea())
                    .description(property.getDescription())
                    .price(property.getPrice())
                    .streetName(property.getStreetName())
                    .postCode(property.getPostCode())
                    .ownerName(ownerName)
                    .cityName(cityName)
                    .imageUrl(property.getImageUrls().get(0))
                    .build();

            //add the response above to the responses list
            responses.add(response);
        }

        //return responses to controller
        return responses;
    }
}
