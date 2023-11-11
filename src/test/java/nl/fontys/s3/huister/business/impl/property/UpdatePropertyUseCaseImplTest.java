package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
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
class UpdatePropertyUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private UpdatePropertyUseCaseImpl updatePropertyUseCase;

    /**
     * @verifies throw new PropertyNotFoundException if property is not found
     * @see UpdatePropertyUseCaseImpl#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest)
     */
    @Test
    void updateProperty_shouldThrowNewPropertyNotFoundExceptionIfPropertyIsNotFound() {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .description("Nice Place")
                .price(450.15)
                .build();

        when(propertyRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->updatePropertyUseCase.updateProperty(request));

    }

    /**
     * @verifies update the chosen property
     * @see UpdatePropertyUseCaseImpl#updateProperty(nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest)
     */
    @Test
    void updateProperty_shouldUpdateTheChosenProperty() {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .description("Nice Place")
                .price(450.15)
                .imageUrl("property.png")
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .id(1L)
                .owner(owner)
                .build();

        when(propertyRepositoryMock.findById(request.getId())).thenReturn(Optional.of(property));

        when(requestAccessTokenMock.getId()).thenReturn(1L);


        //Act
        updatePropertyUseCase.updateProperty(request);

        //Assert
        verify(propertyRepositoryMock).updateProperty(request.getId(),
                request.getImageUrl(),
                request.getDescription(),
                request.getPrice());

    }

    /**
     * @verifies throw new UnauthorizedUserException when property owner is not user
     * @see UpdatePropertyUseCaseImpl#updateProperty(UpdatePropertyRequest)
     */
    @Test
    void updateProperty_shouldThrowNewUnauthorizedUserExceptionWhenPropertyOwnerIsNotUser() {
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1L)
                .description("Nice Place")
                .price(450.15)
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .id(1L)
                .owner(owner)
                .build();

        when(propertyRepositoryMock.findById(request.getId())).thenReturn(Optional.of(property));

        when(requestAccessTokenMock.getId()).thenReturn(2L);

        //Act + Assert
        assertThrows(UnauthorizedUserException.class,()->updatePropertyUseCase.updateProperty(request));
    }
}
