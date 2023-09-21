package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.city.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.model.City;
import nl.fontys.s3.huister.model.Property;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.model.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllPropertiesUseCaseImplTest {
    @Mock
    private CityRepository cityRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @InjectMocks
    private GetAllPropertiesUseCaseImpl getAllPropertiesUseCase;
    /**
     * @verifies throw UserNotFoundException when cannot get user data from userId parameter
     * @see GetAllPropertiesUseCaseImpl#getAllProperties(int)
     */
    @Test
    public void GetAllProperties_shouldThrowUserNotFoundExceptionWhenCannotGetUserDataFromUserIdParameter() {
        //Arrange
        Property property=Property.builder()
                .id(1)
                .ownerId(2)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("imageProperty1.png"))
                .isRented(true)
                .area(10)
                .build();

        User user=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.ADMIN)
                .profilePictureUrl("imageUser1.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(propertyRepositoryMock.getAllProperties()).thenReturn(List.of(property));
        when(userRepositoryMock.getUserById(property.getOwnerId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllPropertiesUseCase.getAllProperties(user.getId()));
    }

    /**
     * @verifies throw CityNotFoundException when cannot get city data from cityId parameter
     * @see GetAllPropertiesUseCaseImpl#getAllProperties(int)
     */
    @Test
    public void GetAllProperties_shouldThrowCityNotFoundExceptionWhenCannotGetCityDataFromCityIdParameter() {
        //Arrange
        Property property=Property.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("imageProperty1.png"))
                .isRented(true)
                .area(10)
                .build();

        User user=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("imageUser1.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(propertyRepositoryMock.getPropertiesByOwner(user.getId())).thenReturn(List.of(property));
        when(userRepositoryMock.getUserById(property.getOwnerId())).thenReturn(Optional.of(user));
        when(cityRepositoryMock.getCityById(property.getCityId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(CityNotFoundException.class,()->getAllPropertiesUseCase.getAllProperties(user.getId()));
    }

    /**
     * @verifies return List of responses when all data are valid
     * @see GetAllPropertiesUseCaseImpl#getAllProperties(int)
     */
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN","OWNER","CUSTOMER"})
    public void GetAllProperties_shouldReturnListOfResponsesWhenAllDataAreValid(UserRole role) {
        Property property1=Property.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("image1.png","image2.png"))
                .isRented(true)
                .area(10)
                .build();

        Property property2=Property.builder()
                .id(2)
                .ownerId(2)
                .cityId(1)
                .streetName("straat")
                .description("Bad Place")
                .postCode("2222BB")
                .price(500)
                .imageUrls(List.of("image3.png","image4.png"))
                .isRented(false)
                .area(12)
                .build();

        User user1=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.valueOf(String.valueOf(role)))
                .profilePictureUrl("Image5.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();
        User user2=User.builder()
                .id(2)
                .username("user2")
                .role(UserRole.valueOf(String.valueOf(role)))
                .profilePictureUrl("Image6.png")
                .password("user2")
                .name("user2")
                .phoneNumber("9876543210")
                .build();

        City city1=City.builder()
                .id(1)
                .name("city1")
                .build();

        GetAllPropertiesResponse responseProperty1=GetAllPropertiesResponse.builder()
                .id(property1.getId())
                .area(property1.getArea())
                .description(property1.getDescription())
                .price(property1.getPrice())
                .streetName(property1.getStreetName())
                .postCode(property1.getPostCode())
                .ownerName(user1.getName())
                .cityName(city1.getName())
                .imageUrl(property1.getImageUrls().get(0))
                .build();

        GetAllPropertiesResponse responseProperty2=GetAllPropertiesResponse.builder()
                .id(property2.getId())
                .area(property2.getArea())
                .description(property2.getDescription())
                .price(property2.getPrice())
                .streetName(property2.getStreetName())
                .postCode(property2.getPostCode())
                .ownerName(user2.getName())
                .cityName(city1.getName())
                .imageUrl(property2.getImageUrls().get(0))
                .build();

        List<GetAllPropertiesResponse>responsesAdmin=List.of(responseProperty1,responseProperty2);
        List<GetAllPropertiesResponse>responsesOwner=List.of(responseProperty1);
        List<GetAllPropertiesResponse>responsesCustomer=List.of(responseProperty2);


        when(userRepositoryMock.getUserById(user1.getId())).thenReturn(Optional.of(user1));
        when(cityRepositoryMock.getCityById(property1.getCityId())).thenReturn(Optional.of(city1));

        List<GetAllPropertiesResponse> expectedResponses=switch (user1.getRole()){
            case ADMIN -> {
                when(propertyRepositoryMock.getAllProperties()).thenReturn(List.of(property1,property2));
                when(userRepositoryMock.getUserById(property2.getOwnerId())).thenReturn(Optional.of(user2));
                yield responsesAdmin;
            }
            case OWNER -> {
                when(propertyRepositoryMock.getPropertiesByOwner(user1.getId())).thenReturn(List.of(property1));
                yield responsesOwner;
            }
            case CUSTOMER -> {
                when(propertyRepositoryMock.getAllNotRentedProperties()).thenReturn(List.of(property2));
                when(userRepositoryMock.getUserById(property2.getOwnerId())).thenReturn(Optional.of(user2));
                yield responsesCustomer;
            }
        };
        //Act
        List<GetAllPropertiesResponse> actualResponses=getAllPropertiesUseCase.getAllProperties(user1.getId());
        //Assert
        assertEquals(expectedResponses,actualResponses);
    }
}