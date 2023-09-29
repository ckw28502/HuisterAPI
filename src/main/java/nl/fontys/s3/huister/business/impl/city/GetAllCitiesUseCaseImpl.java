package nl.fontys.s3.huister.business.impl.city;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.model.City;
import nl.fontys.s3.huister.model.Property;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class GetAllCitiesUseCaseImpl implements GetAllCitiesUseCase {
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    /**
     *
     * @param userId Logged in user id
     * @return GetAllCitiesResponse containing list of cities
     *
     * @should throw UserNotFoundException when user is not found
     * @should return list of cities according to logged in user's role
     */
    @Override
    public GetAllCitiesResponse getAllCities(int userId) {
        Optional<User>userOptional=userRepository.getUserById(userId);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user=userOptional.get();
        List<City>cities=switch (user.getRole()) {
            case ADMIN -> cityRepository.getAllCities();
            case OWNER -> cityRepository.getAllCities(getCityIdsForOwner(userId));
            case CUSTOMER -> cityRepository.getAllCities(getCityIdsWhichAreNotRented());
        };
        return GetAllCitiesResponse.builder()
                .cities(cities)
                .build();
    }

    private List<Integer> getCityIdsWhichAreNotRented() {
        return getCityIds(propertyRepository.getAllNotRentedProperties());
    }

    private List<Integer>getCityIdsForOwner(final int ownerId){
        return getCityIds(propertyRepository.getPropertiesByOwner(ownerId));
    }

    private List<Integer> getCityIds(List<Property> properties) {
        List<Integer>cityIds= new ArrayList<>();
        for (Property property:properties){
            int cityId=property.getCityId();
            if (!cityIds.contains(cityId)){
                cityIds.add(cityId);
            }
        }
        return cityIds;
    }
}
