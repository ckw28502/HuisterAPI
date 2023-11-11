package nl.fontys.s3.huister.business.impl.city;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllCitiesUseCaseImpl implements GetAllCitiesUseCase {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @return list of city
     *
     * @should throw UserNotFoundException when user is not found
     * @should return an empty list when there is no city
     * @should return list of cities when cities found according to user's role
     */
    @Override
    public GetAllCitiesResponse getAllCities() {
        Optional<UserEntity>optionalUser=userRepository.findById(requestAccessToken.getId());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();

        List<CityEntity>cities=switch (user.getRole()){
            case ADMIN -> cityRepository.findAll();
            case OWNER -> cityRepository.findByOwnerId(user.getId());
            case CUSTOMER -> cityRepository.findCityByEndRentIsNull();
        };
        return GetAllCitiesResponse.builder()
                .cities(cities)
                .build();
    }
}
