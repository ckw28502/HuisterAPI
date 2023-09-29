package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdatePropertyUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @InjectMocks
    UpdatePropertyUseCaseImpl updatePropertyUseCase;
    /**
     * @verifies throw new PropertyNotFoundException if property is not found in repository
     * @see UpdatePropertyUseCaseImpl#updateProperty(UpdatePropertyRequest)
     */
    @Test
    public void updateProperty_shouldThrowNewPropertyNotFoundExceptionIfPropertyIsNotFoundInRepository() {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1)
                .description("Good House")
                .imageUrls(List.of("image.jpg"))
                .price(600)
                .build();

        when(propertyRepositoryMock.getPropertyById(request.getId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->updatePropertyUseCase.updateProperty(request));
    }

    /**
     * @verifies update the chosen property
     * @see UpdatePropertyUseCaseImpl#updateProperty(UpdatePropertyRequest)
     */
    @Test
    public void updateProperty_shouldUpdateTheChosenProperty() {
        //Arrange
        UpdatePropertyRequest request=UpdatePropertyRequest.builder()
                .id(1)
                .description("Bad House")
                .imageUrls(List.of("image2.jpg"))
                .price(600)
                .build();

        PropertyEntity property= PropertyEntity.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(800)
                .imageUrls(List.of("image1.png"))
                .isRented(false)
                .area(10)
                .build();

        when(propertyRepositoryMock.getPropertyById(request.getId())).thenReturn(Optional.of(property));
        //Act
        updatePropertyUseCase.updateProperty(request);
        //Assert
        verify(propertyRepositoryMock).updateProperty(request);
    }
}
