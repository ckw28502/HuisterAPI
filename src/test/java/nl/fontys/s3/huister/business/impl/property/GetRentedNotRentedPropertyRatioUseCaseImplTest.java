package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.user.InvalidRoleException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRentedNotRentedPropertyRatioUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @InjectMocks
    private GetRentedNotRentedPropertyRatioUseCaseImpl getRentedNotRentedPropertyRatioUseCase;
    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(int)
     */
    @Test
    public void getRentedNotRentedPropertyRatio_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        when(userRepositoryMock.getUserById(1)).thenReturn(Optional.empty());
        //Act + Assert
        Assertions.assertThrows(UserNotFoundException.class,()->getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio(1));
    }

    /**
     * @verifies throw InvalidRoleException when user role is customer
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(int)
     */
    @Test
    public void getRentedNotRentedPropertyRatio_shouldThrowInvalidRoleExceptionWhenUserRoleIsCustomer() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.CUSTOMER)
                .profilePictureUrl("imageUser1.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        //Act + Assert
        Assertions.assertThrows(InvalidRoleException.class,()->getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio(1));

    }

    /**
     * @verifies return the correct response when user role is either admin or owner
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(int)
     */
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN","OWNER"})
    public void getRentedNotRentedPropertyRatio_shouldReturnTheCorrectResponseWhenUserRoleIsEitherAdminOrOwner(UserRole role) {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(2)
                .username("user1")
                .role(role)
                .profilePictureUrl("imageUser1.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        PropertyEntity property1= PropertyEntity.builder()
                .id(1)
                .ownerId(2)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("imageProperty1.png"))
                .isRented(false)
                .area(10)
                .build();

        PropertyEntity property2= PropertyEntity.builder()
                .id(1)
                .ownerId(3)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("imageProperty1.png"))
                .isRented(true)
                .area(10)
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        GetRentedNotRentedPropertyRatioResponse expectedResponse;
        if (role==UserRole.ADMIN){
            when(propertyRepositoryMock.getAllProperties()).thenReturn(List.of(property1,property2));
            expectedResponse=GetRentedNotRentedPropertyRatioResponse.builder()
                    .rented(1)
                    .notRented(1)
                    .build();
        }else{
            when(propertyRepositoryMock.getPropertiesByOwner(user.getId())).thenReturn(List.of(property1));
            expectedResponse=GetRentedNotRentedPropertyRatioResponse.builder()
                    .rented(0)
                    .notRented(1)
                    .build();
        }
        //Act
        GetRentedNotRentedPropertyRatioResponse actualResponse=getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio(user.getId());
        //Assert
        Assertions.assertEquals(expectedResponse,actualResponse);
    }
}
