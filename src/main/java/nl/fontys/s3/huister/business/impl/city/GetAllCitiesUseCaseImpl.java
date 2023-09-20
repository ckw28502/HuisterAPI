package nl.fontys.s3.huister.business.impl.city;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.Model.Property;
import nl.fontys.s3.huister.Model.User;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.domain.response.city.GetAllCitiesResponse;
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

    @Override
    public GetAllCitiesResponse getAllCities(int userId) {
        Optional<User>userOptional=userRepository.getUserById(userId);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        User user=userOptional.get();
        List<City>cities=switch (user.getRole()) {
            case 0 -> cityRepository.getAllCities();
            case 1 -> cityRepository.getAllCities(getCityIdsForOwner(userId));
            case 2 -> cityRepository.getAllCities(getCityIdsWhichAreNotRented());
            default -> throw new IllegalStateException("Unexpected value: " + user.getRole());
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
