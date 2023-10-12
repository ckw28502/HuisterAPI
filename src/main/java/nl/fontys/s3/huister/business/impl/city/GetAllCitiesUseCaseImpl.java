package nl.fontys.s3.huister.business.impl.city;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllCitiesUseCaseImpl implements GetAllCitiesUseCase {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;

    /**
     *
     * @param userId current user id
     * @return list of city according to user role
     *
     * @should throw UserNotFoundException when user is not found
     * @should return list of cities according to logged in user's role
     */
    @Override
    public GetAllCitiesResponse getAllCities(int userId) {
        Optional<UserEntity>optionalUser=userRepository.getUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();


        List<CityEntity>cities=switch (user.getRole()){
            case ADMIN -> cityRepository.getAllCities();
            case OWNER -> getCitiesByProperties(propertyRepository.getPropertiesByOwner(userId));
            case CUSTOMER -> getCitiesByProperties(propertyRepository.getAllNotRentedProperties());
        };
        return GetAllCitiesResponse.builder()
                .cities(cities)
                .build();
    }

    private List<CityEntity> getCitiesByProperties(List<PropertyEntity> properties) {
        List<Integer>propertyIds=properties.stream().map(PropertyEntity::getCityId)
                .distinct().toList();
        return cityRepository.getAllCities(propertyIds);
    }
}
