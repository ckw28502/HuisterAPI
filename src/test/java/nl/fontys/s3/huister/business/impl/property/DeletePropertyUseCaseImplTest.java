package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletePropertyUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private DeletePropertyUseCaseImpl deletePropertyUseCase;

    /**
     * @verifies throw PropertyNotFoundException if id is invalid
     * @see DeletePropertyUseCaseImpl#deleteProperty(long)
     */
    @Test
    void deleteProperty_shouldThrowPropertyNotFoundExceptionIfIdIsInvalid() {
        //Arrange
        when(propertyRepositoryMock.findByIdAndIsDeletedIsNull(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->deletePropertyUseCase.deleteProperty(1L));

    }

    /**
     * @verifies delete property
     * @see DeletePropertyUseCaseImpl#deleteProperty(long)
     */
    @Test
    void deleteProperty_shouldDeleteProperty() {
        //Arrange
        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .id(1L)
                .owner(owner)
                .build();

        when(propertyRepositoryMock.findByIdAndIsDeletedIsNull(property.getId())).thenReturn(Optional.of(property));
        when(requestAccessTokenMock.getId()).thenReturn(1L);

        //Act
        deletePropertyUseCase.deleteProperty(property.getId());

        //Assert
        verify(propertyRepositoryMock).deleteProperty(property.getId());

    }

    /**
     * @verifies throw UnauthorizedUserException when user is unauthorized
     * @see DeletePropertyUseCaseImpl#deleteProperty(long)
     */
    @Test
    void deleteProperty_shouldThrowUnauthorizedUserExceptionWhenUserIsUnauthorized() {
        //Arrange
        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .id(1L)
                .owner(owner)
                .build();

        when(propertyRepositoryMock.findByIdAndIsDeletedIsNull(property.getId())).thenReturn(Optional.of(property));
        when(requestAccessTokenMock.getId()).thenReturn(2L);

        //Act + Assert
        assertThrows(UnauthorizedUserException.class,()->deletePropertyUseCase.deleteProperty(1L));

    }
}
