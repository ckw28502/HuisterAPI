package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.business.GetAllPropertiesForCustomerUseCase;
import nl.fontys.s3.huister.business.exception.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.UserNotFoundException;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesForCustomerResponse;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.Model.Property;
import nl.fontys.s3.huister.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllPropertiesForCustomerUseCaseImpl implements GetAllPropertiesForCustomerUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    @Override
    public List<GetAllPropertiesForCustomerResponse> GetAllPropertiesForCustomer() {

        //Get all properties that is not rented yet
        List<Property>properties=propertyRepository.getAllNotRentedProperties();

        //Define list of response
        List<GetAllPropertiesForCustomerResponse>responses=new ArrayList<>();


        for (Property property:properties){

            //Get owner name
            Optional<User> owner=userRepository.getUserById(property.getOwnerId());
            if (owner.isEmpty()){
                throw new UserNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String ownerName=owner.get().getName();

            //Get city name
            Optional<City> city=cityRepository.getCityById(property.getCityId());
            if (city.isEmpty()){
                throw new CityNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String cityName=city.get().getName();

            //add all current property's data to the responses
            GetAllPropertiesForCustomerResponse response=GetAllPropertiesForCustomerResponse.builder()
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
            responses.add(response);
        }
        //return responses to controller
        return responses;
    }
}
