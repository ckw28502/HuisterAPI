package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
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

    @InjectMocks
    private DeletePropertyUseCaseImpl deletePropertyUseCase;

    /**
     * @verifies throw PropertyNotFoundException if id is invalid
     * @see DeletePropertyUseCaseImpl#deleteProperty(long)
     */
    @Test
    void deleteProperty_shouldThrowPropertyNotFoundExceptionIfIdIsInvalid() {
        //Arrange
        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.empty());

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
        PropertyEntity property=PropertyEntity.builder().build();

        when(propertyRepositoryMock.findById(property.getId())).thenReturn(Optional.of(property));

        //Act
        deletePropertyUseCase.deleteProperty(property.getId());

        //Assert
        verify(propertyRepositoryMock).deleteProperty(property.getId());

    }
}
