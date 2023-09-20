package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.business.interfaces.property.GetAllPropertiesUseCase;
import nl.fontys.s3.huister.business.exception.city.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.Model.Property;
import nl.fontys.s3.huister.Model.User;
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

    @Override
    public List<GetAllPropertiesResponse> GetAllProperties(int id) {

        //get current logged in user
        User user=userRepository.getUserById(id).get();

        //get retrieved data based on user role
        List<Property>properties=switch (user.getRole()){
            case 0:
                yield propertyRepository.getAllProperties();
            case 1:
                yield propertyRepository.getPropertiesByOwner(user.getId());
            case 2:
                yield propertyRepository.getAllNotRentedProperties();
            default:
                throw new IllegalStateException("Unexpected value: " + user.getRole());
        };

        //Define list of response
        List<GetAllPropertiesResponse>responses=new ArrayList<>();

        //iterate the property list
        for (Property property:properties){

            //Get owner name
            Optional<User> owner=userRepository.getUserById(property.getOwnerId());
            if (owner.isEmpty()){
                throw new UserNotFoundException();
            }
            String ownerName=owner.get().getName();

            //Get city name
            Optional<City> city=cityRepository.getCityById(property.getCityId());
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
