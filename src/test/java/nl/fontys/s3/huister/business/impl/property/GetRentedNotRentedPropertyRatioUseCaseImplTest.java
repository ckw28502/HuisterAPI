package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.user.InvalidRoleException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRentedNotRentedPropertyRatioUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private GetRentedNotRentedPropertyRatioUseCaseImpl getRentedNotRentedPropertyRatioUseCase;

    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(long)
     */
    @Test
    void getRentedNotRentedPropertyRatio_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->
                getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio(1L));

    }

    /**
     * @verifies throw InvalidRoleException when user role is customer
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(long)
     */
    @Test
    void getRentedNotRentedPropertyRatio_shouldThrowInvalidRoleExceptionWhenUserRoleIsCustomer() {
        //Arrange
        UserEntity user=UserEntity.builder()
                .role(UserRole.CUSTOMER)
                .id(1L)
                .build();

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));

        //Act + Assert
        assertThrows(InvalidRoleException.class,
                ()->getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio(1L));

    }

    /**
     * @verifies return the correct response when user role is either admin or owner
     * @see GetRentedNotRentedPropertyRatioUseCaseImpl#getRentedNotRentedPropertyRatio(long)
     */
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN","OWNER"})
    void getRentedNotRentedPropertyRatio_shouldReturnTheCorrectResponseWhenUserRoleIsEitherAdminOrOwner(UserRole role) {
        //Arrange
        UserEntity user=UserEntity.builder()
                .role(role)
                .id(1L)
                .build();

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        int rentedProperty=1;
        int notRentedProperty=1;
        switch (role){
            case ADMIN -> {
                when(propertyRepositoryMock.countByEndRentIsNull()).thenReturn(notRentedProperty);
                when(propertyRepositoryMock.countByEndRentIsNotNull()).thenReturn(rentedProperty);
            }
            case OWNER -> {
                when(propertyRepositoryMock.countByEndRentIsNotNullAndOwner(user)).thenReturn(rentedProperty);
                when(propertyRepositoryMock.countByEndRentIsNullAndOwner(user)).thenReturn(notRentedProperty);
            }
        }

        GetRentedNotRentedPropertyRatioResponse expectedResponse=GetRentedNotRentedPropertyRatioResponse.builder()
                .rented(rentedProperty)
                .notRented(notRentedProperty)
                .build();

        //Act
        GetRentedNotRentedPropertyRatioResponse actualResponse=getRentedNotRentedPropertyRatioUseCase
                .getRentedNotRentedPropertyRatio(user.getId());

        //Assert
        assertEquals(expectedResponse,actualResponse);

    }
}
