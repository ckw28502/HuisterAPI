package nl.fontys.s3.huister.business.impl.property;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.property.GetAllPropertiesUseCase;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllPropertiesUseCaseImpl implements GetAllPropertiesUseCase {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @return List of properties data
     *
     * @should throw UserNotFoundException when user is not found
     * @should return an empty list when there is no appropriate property
     * @should return List of responses when all data are valid
     */
    @Override
    public List<GetAllPropertiesResponse> getAllProperties() {

        //get current logged in user
        Optional<UserEntity> optionalUser=userRepository.findById(requestAccessToken.getId());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();

        //get retrieved data based on user role
        List<PropertyEntity>properties=switch (user.getRole()){
            case ADMIN:
                yield propertyRepository.findAllByIsDeletedIsNull();
            case OWNER:
                yield propertyRepository.findAllByOwnerIdAndIsDeletedIsNull(user.getId());
            case CUSTOMER:
                yield propertyRepository.findAllByEndRentIsNull();
        };

        //Define list of response
        List<GetAllPropertiesResponse>responses=new ArrayList<>();

        //iterate the property list
        for (PropertyEntity property:properties){

            //add all current property's data to the response
            GetAllPropertiesResponse response= GetAllPropertiesResponse.builder()
                    .id(property.getId())
                    .area(property.getArea())
                    .description(property.getDescription())
                    .price(property.getPrice())
                    .streetName(property.getStreetName())
                    .postCode(property.getPostCode())
                    .ownerName(property.getOwner().getName())
                    .cityName(property.getCity().getName())
                    .cityId(property.getCity().getId())
                    .imageUrl(property.getImageUrl())
                    .build();

            //add the response above to the responses list
            responses.add(response);
        }

        //return responses to controller
        return responses;
    }
}
