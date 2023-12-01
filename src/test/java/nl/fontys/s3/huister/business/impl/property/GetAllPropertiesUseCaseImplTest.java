package nl.fontys.s3.huister.business.impl.property;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.CityEntity;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllPropertiesUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private GetAllPropertiesUseCaseImpl getAllPropertiesUseCase;


    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see GetAllPropertiesUseCaseImpl#getAllProperties()
     */
    @Test
    void getAllProperties_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllPropertiesUseCase.getAllProperties());

    }

    /**
     * @verifies return List of responses when all data are valid
     * @see GetAllPropertiesUseCaseImpl#getAllProperties()
     */
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN","OWNER","CUSTOMER"})
    void getAllProperties_shouldReturnListOfResponsesWhenAllDataAreValid(UserRole role) {
        //Arrange
        UserEntity user1=UserEntity.builder()
                .name("user1")
                .role(role)
                .build();

        UserEntity user2=UserEntity.builder()
                .name("user2")
                .role(UserRole.OWNER)
                .build();

        CityEntity city=CityEntity.builder()
                .name("city")
                .id(1L)
                .build();

        PropertyEntity property1=PropertyEntity.builder()
                .id(1L)
                .area(12.95)
                .description("Good Place")
                .price(400)
                .streetName("property1 street")
                .postCode("1111AA")
                .owner(user1)
                .city(city)
                .imageUrl("property1.jpg")
                .build();

        PropertyEntity property2=PropertyEntity.builder()
                .id(2L)
                .area(14.2)
                .description("Nice Place")
                .price(480)
                .streetName("property2 street")
                .postCode("2222BB")
                .owner(user2)
                .city(city)
                .imageUrl("property2.jpg")
                .build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(user1));

        List<PropertyEntity>properties=new ArrayList<>();
        switch (role){
            case ADMIN -> {
                properties.addAll(List.of(property1,property2));
                when(propertyRepositoryMock.findAllByIsDeletedIsNull()).thenReturn(properties);
            }
            case OWNER -> {
                properties.add((property1));
                when(propertyRepositoryMock.findAllByOwnerIdAndIsDeletedIsNull(user1.getId())).thenReturn(properties);
            }
            case CUSTOMER -> {
                properties.add((property2));
                when(propertyRepositoryMock.findAllByEndRentIsNull()).thenReturn(properties);
            }
        }

        List<GetAllPropertiesResponse> expectedResponses=properties.stream().map(property-> GetAllPropertiesResponse.builder()
                .id(property.getId())
                .area(property.getArea())
                .description(property.getDescription())
                .price(property.getPrice())
                .streetName(property.getStreetName())
                .postCode(property.getPostCode())
                .ownerName(property.getOwner().getName())
                .cityName(property.getCity().getName())
                .cityId(property.getCity().getId())
                .imageUrl(property.getImageUrl())
                .build()).toList();

        //Act
        List<GetAllPropertiesResponse>actualResponses=getAllPropertiesUseCase.getAllProperties();

        //Assert
        assertEquals(expectedResponses,actualResponses);

    }

    /**
     * @verifies return an empty list when there is no appropriate property
     * @see GetAllPropertiesUseCaseImpl#getAllProperties()
     */
    @Test
    void getAllProperties_shouldReturnAnEmptyListWhenThereIsNoAppropriateProperty() {
        //Arrange
        UserEntity user1=UserEntity.builder()
                .name("user1")
                .role(UserRole.OWNER)
                .build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(user1));

        //Act
        List<GetAllPropertiesResponse>responses=getAllPropertiesUseCase.getAllProperties();

        //Assert
        assert responses.isEmpty();
    }
}
