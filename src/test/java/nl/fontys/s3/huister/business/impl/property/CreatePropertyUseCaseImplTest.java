package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
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
class CreatePropertyUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private CreatePropertyUseCaseImpl createPropertyUseCase;

    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see CreatePropertyUseCaseImpl#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    void createProperty_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        CreatePropertyRequest request = CreatePropertyRequest.builder().build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->createPropertyUseCase.createProperty(request));

    }

    /**
     * @verifies create property
     * @see CreatePropertyUseCaseImpl#createProperty(nl.fontys.s3.huister.business.request.property.CreatePropertyRequest)
     */
    @Test
    void createProperty_shouldCreateProperty() {
        //Arrange
        CreatePropertyRequest request = CreatePropertyRequest.builder().build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(owner));

        //Act
        createPropertyUseCase.createProperty(request);

        //Assert
        verify(propertyRepositoryMock).saveProperty(
                owner.getId(),
                request.getCityName(),
                request.getStreetName(),
                request.getPostCode(),
                request.getDescription(),
                request.getImageUrl(),
                request.getArea(),
                request.getPrice(),
                request.getHouseNumber()
        );

    }
}
