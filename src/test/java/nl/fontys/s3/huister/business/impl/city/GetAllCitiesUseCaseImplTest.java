package nl.fontys.s3.huister.business.impl.city;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.domain.response.city.GetAllCitiesResponse;
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
public class GetAllCitiesUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private GetAllCitiesUseCaseImpl getAllCitiesUseCase;
    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see GetAllCitiesUseCaseImpl#getAllCities(int)
     */
    @Test
    public void getAllCities_shouldThrowUserNotFoundExceptionWhenUserIsNotFound(){
        //Arrange
        when(userRepositoryMock.getUserById(1)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllCitiesUseCase.getAllCities(1));
    }

    /**
     * @verifies return list of cities according to logged in user's role
     * @see GetAllCitiesUseCaseImpl#getAllCities(int)
     */
    @ParameterizedTest
    @ValueSource(strings={"ADMIN","OWNER","CUSTOMER"})
    public void getAllCities_shouldReturnListOfCitiesAccordingToLoggedInUsersRole(UserRole role) {
        //Arrange
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
                .cityId(2)
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

        City city1=City.builder()
                .id(1)
                .name("city1")
                .build();

        City city2=City.builder()
                .id(1)
                .name("city2")
                .build();

        GetAllCitiesResponse responseAdmin=GetAllCitiesResponse.builder()
                .cities(List.of(city1,city2))
                .build();

        GetAllCitiesResponse responseOwner=GetAllCitiesResponse.builder()
                .cities(List.of(city1))
                .build();

        GetAllCitiesResponse responseCustomer=GetAllCitiesResponse.builder()
                .cities(List.of(city2))
                .build();

        when(userRepositoryMock.getUserById(user1.getId())).thenReturn(Optional.of(user1));

        GetAllCitiesResponse expectedResponse=switch (user1.getRole()){
            case ADMIN -> {
                when(cityRepositoryMock.getAllCities()).thenReturn(List.of(city1,city2));
                yield responseAdmin;
            }
            case OWNER -> {
                when(propertyRepositoryMock.getPropertiesByOwner(user1.getId())).thenReturn(List.of(property1));
                when(cityRepositoryMock.getAllCities(List.of(1))).thenReturn(List.of(city1));
                yield responseOwner;
            }
            case CUSTOMER -> {
                when(propertyRepositoryMock.getAllNotRentedProperties()).thenReturn(List.of(property2));
                when(cityRepositoryMock.getAllCities(List.of(2))).thenReturn(List.of(city2));
                yield responseCustomer;
            }
        };
        //Act
        GetAllCitiesResponse actualResponse=getAllCitiesUseCase.getAllCities(user1.getId());
        //Assert
        assertEquals(expectedResponse,actualResponse);
    }
}
