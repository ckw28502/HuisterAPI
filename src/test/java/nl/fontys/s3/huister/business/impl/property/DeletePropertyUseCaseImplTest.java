package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.model.Property;
import nl.fontys.s3.huister.persistence.CityRepository;
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
public class DeletePropertyUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private DeletePropertyUseCaseImpl deletePropertyUseCase;
    /**
     * @verifies throw PropertyNotFoundException if id is invalid
     * @see DeletePropertyUseCaseImpl#deleteProperty(int)
     */
    @Test
    public void deleteProperty_shouldThrowPropertyNotFoundExceptionIfIdIsInvalid() {
        //Arrange
        int id=1;

        when(propertyRepositoryMock.getPropertyById(id)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->deletePropertyUseCase.deleteProperty(id));
    }

    /**
     * @verifies delete property if id is valid
     * @see DeletePropertyUseCaseImpl#deleteProperty(int)
     */
    @Test
    public void deleteProperty_shouldDeletePropertyIfIdIsValid() {
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
        int id=1;

        when(propertyRepositoryMock.getPropertyById(id)).thenReturn(Optional.of(property1));
        when(propertyRepositoryMock.isCityHasNoProperty(property1.getCityId())).thenReturn(false);
        //Act
        deletePropertyUseCase.deleteProperty(id);
        //Assert
        verify(propertyRepositoryMock).deleteProperty(id);
    }

    /**
     * @verifies delete city object if there are no property in the city
     * @see DeletePropertyUseCaseImpl#deleteProperty(int)
     */
    @Test
    public void deleteProperty_shouldDeleteCityObjectIfThereAreNoPropertyInTheCity() {
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
        int id=1;

        when(propertyRepositoryMock.getPropertyById(id)).thenReturn(Optional.of(property1));
        when(propertyRepositoryMock.isCityHasNoProperty(property1.getCityId())).thenReturn(true);
        //Act
        deletePropertyUseCase.deleteProperty(id);
        //Assert
        verify(propertyRepositoryMock).deleteProperty(id);
        verify(cityRepositoryMock).deleteCity(id);
    }
}
