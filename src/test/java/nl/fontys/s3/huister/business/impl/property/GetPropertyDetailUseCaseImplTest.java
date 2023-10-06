package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPropertyDetailUseCaseImplTest {
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private GetPropertyDetailUseCaseImpl getPropertyDetailUseCase;

    /**
     * @verifies throw PropertyNotFoundException when property can't be found
     * @see GetPropertyDetailUseCaseImpl#getPropertyDetail(int)
     */
    @Test
    public void getPropertyDetail_shouldThrowPropertyNotFoundExceptionWhenPropertyCantBeFound() {
        //Arrange
        int id=1;
        when(propertyRepositoryMock.getPropertyById(id)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->getPropertyDetailUseCase.getPropertyDetail(id));
    }

    /**
     * @verifies return appropriate response after a successful validation
     * @see GetPropertyDetailUseCaseImpl#getPropertyDetail(int)
     */
    @Test
    public void getPropertyDetail_shouldReturnAppropriateResponseAfterASuccessfulValidation() {
       //Arrange
        PropertyEntity property1= PropertyEntity.builder()
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

        UserEntity user1= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.ADMIN)
                .profilePictureUrl("Image5.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        CityEntity city1= CityEntity.builder()
                .id(1)
                .name("city1")
                .build();

        GetPropertyDetailResponse expectedResponse=GetPropertyDetailResponse.builder()
                .id(property1.getId())
                .price(property1.getPrice())
                .description(property1.getDescription())
                .imageUrls(property1.getImageUrls())
                .ownerId(property1.getOwnerId())
                .streetName(property1.getStreetName())
                .ownerName(user1.getName())
                .postCode(property1.getPostCode())
                .area(property1.getArea())
                .cityName(city1.getName())
                .build();

        when(propertyRepositoryMock.getPropertyById(property1.getId())).thenReturn(Optional.of(property1));
        when(userRepositoryMock.getUserById(property1.getOwnerId())).thenReturn(Optional.of(user1));
        when(cityRepositoryMock.getCityById(property1.getCityId())).thenReturn(Optional.of(city1));
       //Act
       GetPropertyDetailResponse actualResponse=getPropertyDetailUseCase.getPropertyDetail(property1.getId());
       //Assert
        assertEquals(expectedResponse,actualResponse);
    }
}
