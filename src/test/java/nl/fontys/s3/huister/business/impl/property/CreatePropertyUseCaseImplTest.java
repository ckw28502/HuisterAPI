package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.model.City;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePropertyUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private CreatePropertyUseCaseImpl createPropertyUseCase;
    /**
     * @verifies create new City object when cityName doesn't exist in CityRepository then create the property
     * @see CreatePropertyUseCaseImpl#createProperty(nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest)
     */
    @Test
    public void createProperty_shouldCreateNewCityObjectWhenCityNameDoesntExistInCityRepositoryThenCreateTheProperty() {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder()
                .ownerId(1)
                .cityName("city1")
                .price(600)
                .postCode("1111AA")
                .streetName("Street")
                .area(10)
                .description("Good Place")
                .imageUrls(List.of("image1.jpg","image2.png"))
                .build();

        City city1=City.builder()
                .id(1)
                .name("city1")
                .build();

        request.setCityId(city1.getId());


        when(cityRepositoryMock.cityNameExists(request.getCityName())).thenReturn(false);
        when(cityRepositoryMock.createCity(request.getCityName())).thenReturn(city1.getId());

        doAnswer((invocation) -> {
            assertEquals(request,invocation.getArgument(0,CreatePropertyRequest.class));
            return null;
        }).when(propertyRepositoryMock).createProperty(request);
        //Act
        createPropertyUseCase.createProperty(request);
        //Assert
        verify(propertyRepositoryMock).createProperty(request);
    }

    /**
     * @verifies add the existing cityId to request before creating new Property
     * @see CreatePropertyUseCaseImpl#createProperty(nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest)
     */
    @Test
    public void createProperty_shouldAddTheExistingCityIdToRequestBeforeCreatingNewProperty() {
        //Arrange
        CreatePropertyRequest request=CreatePropertyRequest.builder()
                .ownerId(1)
                .cityName("city1")
                .price(600)
                .postCode("1111AA")
                .streetName("Street")
                .area(10)
                .description("Good Place")
                .imageUrls(List.of("image1.jpg","image2.png"))
                .build();

        City city1=City.builder()
                .id(1)
                .name("city1")
                .build();

        request.setCityId(city1.getId());

        when(cityRepositoryMock.cityNameExists(request.getCityName())).thenReturn(true);
        when(cityRepositoryMock.getCityByName(request.getCityName())).thenReturn(Optional.of(city1));

        doAnswer((invocation) -> {
            assertEquals(request,invocation.getArgument(0,CreatePropertyRequest.class));
            return null;
        }).when(propertyRepositoryMock).createProperty(request);
        //Act
        createPropertyUseCase.createProperty(request);
        //Assert
        verify(propertyRepositoryMock).createProperty(request);
    }
}
