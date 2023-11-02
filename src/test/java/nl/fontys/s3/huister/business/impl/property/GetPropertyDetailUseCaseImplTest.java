package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPropertyDetailUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;

    @InjectMocks
    private GetPropertyDetailUseCaseImpl getPropertyDetailUseCase;

    /**
     * @verifies throw PropertyNotFoundException when property is not found
     * @see GetPropertyDetailUseCaseImpl#getPropertyDetail(long)
     */
    @Test
    void getPropertyDetail_shouldThrowPropertyNotFoundExceptionWhenPropertyIsNotFound() {
        //Arrange
        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->getPropertyDetailUseCase.getPropertyDetail(1L));

    }

    /**
     * @verifies return property when property is found
     * @see GetPropertyDetailUseCaseImpl#getPropertyDetail(long)
     */
    @Test
    void getPropertyDetail_shouldReturnPropertyWhenPropertyIsFound() {
        //Arrange
        UserEntity owner=UserEntity.builder()
                .id(1L)
                .build();

        CityEntity city=CityEntity.builder()
                .id(1L)
                .name("city")
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .id(1L)
                .price(398.25)
                .description("Good place!")
                .imageUrl("property.png")
                .owner(owner)
                .streetName("property street")
                .postCode("1111AA")
                .area(9.73)
                .city(city)
                .build();

        when(propertyRepositoryMock.findById(property.getId())).thenReturn(Optional.of(property));

        GetPropertyDetailResponse expectedResponse=GetPropertyDetailResponse.builder()
                .id(property.getId())
                .price(property.getPrice())
                .description(property.getDescription())
                .imageUrl(property.getImageUrl())
                .ownerId(property.getOwner().getId())
                .streetName(property.getStreetName())
                .ownerName(property.getOwner().getName())
                .postCode(property.getPostCode())
                .area(property.getArea())
                .cityName(property.getCity().getName())
                .build();

        //Act
        GetPropertyDetailResponse actualResponse=getPropertyDetailUseCase.getPropertyDetail(1L);

        //Assert
        assertEquals(expectedResponse,actualResponse);

    }
}
