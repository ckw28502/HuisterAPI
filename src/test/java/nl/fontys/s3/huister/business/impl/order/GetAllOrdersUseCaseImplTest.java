package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.city.CityNotFoundException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllOrdersUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private GetAllOrdersUseCaseImpl getAllOrdersUseCase;
    /**
     * @verifies throw an UserNotFoundException when user is not found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(int)
     */
    @Test
    public void getAllOrders_shouldThrowAnUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        int userId=1;
        when(userRepositoryMock.getUserById(userId)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllOrdersUseCase.getAllOrders(userId));
    }

    /**
     * @verifies return list of orders when user is found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(int)
     */
    @Test
    public void getAllOrders_shouldReturnListOfOrdersWhenUserIsFound() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        OrderEntity order= OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        PropertyEntity property= PropertyEntity.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("image1.png","image2.png"))
                .isRented(false)
                .area(10)
                .endRent(LocalDate.now())
                .build();

        CityEntity city=CityEntity.builder()
                .id(1)
                .name("city1")
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.getAllOrder(user.getId())).thenReturn(List.of(order));
        when(propertyRepositoryMock.getPropertyById(order.getPropertyId())).thenReturn(Optional.of(property));
        when(cityRepositoryMock.getCityById(property.getCityId())).thenReturn(Optional.of(city));

        List<GetAllOrdersResponse>expectedResponse=List.of(GetAllOrdersResponse.builder()
                .endRent(property.getEndRent().toString())
                .status(order.getStatus())
                .cityName(city.getName())
                .price(order.getPrice())
                .streetName(property.getStreetName())
                .imageUrl(property.getImageUrls().get(0))
                .build());

        //Act
        List<GetAllOrdersResponse> actualResponse=getAllOrdersUseCase.getAllOrders(user.getId());
        //Assert
        assertEquals(expectedResponse,actualResponse);
    }

    /**
     * @verifies throw a PropertyNotFoundException when property is not found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(int)
     */
    @Test
    public void getAllOrders_shouldThrowAPropertyNotFoundExceptionWhenPropertyIsNotFound() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        OrderEntity order= OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.getAllOrder(user.getId())).thenReturn(List.of(order));
        when(propertyRepositoryMock.getPropertyById(order.getPropertyId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->getAllOrdersUseCase.getAllOrders(user.getId()));
    }

    /**
     * @verifies throw a CityNotFoundException when city is not found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(int)
     */
    @Test
    public void getAllOrders_shouldThrowACityNotFoundExceptionWhenCityIsNotFound() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        OrderEntity order= OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        PropertyEntity property= PropertyEntity.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("image1.png","image2.png"))
                .isRented(false)
                .area(10)
                .endRent(LocalDate.now())
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.getAllOrder(user.getId())).thenReturn(List.of(order));
        when(propertyRepositoryMock.getPropertyById(order.getPropertyId())).thenReturn(Optional.of(property));
        when(cityRepositoryMock.getCityById(property.getCityId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(CityNotFoundException.class,()->getAllOrdersUseCase.getAllOrders(user.getId()));
    }
}
