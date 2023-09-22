package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.price.PriceMustBeMoreThanZeroException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.domain.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.model.Property;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.model.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseImplTest {
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;
    /**
     * @verifies throw UserNotFoundException when owner or customer is not in the repository
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.domain.request.order.CreateOrderRequest)
     */
    @ParameterizedTest
    @ValueSource(strings = {"OWNER","CUSTOMER"})
    public void createOrder_shouldThrowUserNotFoundExceptionWhenOwnerOrCustomerIsNotInTheRepository(String role) {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        User user1=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        if (role.equalsIgnoreCase("OWNER")){
            when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.empty());
        } else {
            when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.of(user1));
            when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.empty());
        }
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->createOrderUseCase.createOrder(request));
    }

    /**
     * @verifies throw PropertyNotFoundException when property is not found in the repository
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.domain.request.order.CreateOrderRequest)
     */
    @Test
    public void createOrder_shouldThrowPropertyNotFoundExceptionWhenPropertyIsNotFoundInTheRepository() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        User user1=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        User user2=User.builder()
                .id(2)
                .username("user2")
                .role(UserRole.CUSTOMER)
                .profilePictureUrl("Image.png")
                .password("user2")
                .name("user2")
                .phoneNumber("9876543210")
                .build();

        when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.of(user1));
        when(userRepositoryMock.getUserById(request.getCustomerId())).thenReturn(Optional.of(user2));
        when(propertyRepositoryMock.getPropertyById(request.getPropertyId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->createOrderUseCase.createOrder(request));
    }

    /**
     * @verifies throw PriceMustBeMoreThanZeroException when price is equals or below zero
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.domain.request.order.CreateOrderRequest)
     */
    @Test
    public void createOrder_shouldThrowPriceMustBeMoreThanZeroExceptionWhenPriceIsEqualsOrBelowZero() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(0)
                .build();

        User user1=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        User user2=User.builder()
                .id(2)
                .username("user2")
                .role(UserRole.CUSTOMER)
                .profilePictureUrl("Image.png")
                .password("user2")
                .name("user2")
                .phoneNumber("9876543210")
                .build();

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

        when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.of(user1));
        when(userRepositoryMock.getUserById(request.getCustomerId())).thenReturn(Optional.of(user2));
        when(propertyRepositoryMock.getPropertyById(request.getPropertyId())).thenReturn(Optional.of(property1));

        //Act + Assert
        assertThrows(PriceMustBeMoreThanZeroException.class,()->createOrderUseCase.createOrder(request));
    }

    /**
     * @verifies create new Order object when request data are valid
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.domain.request.order.CreateOrderRequest)
     */
    @Test
    public void createOrder_shouldCreateNewOrderObjectWhenRequestDataAreValid() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        User user1=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        User user2=User.builder()
                .id(2)
                .username("user2")
                .role(UserRole.CUSTOMER)
                .profilePictureUrl("Image.png")
                .password("user2")
                .name("user2")
                .phoneNumber("9876543210")
                .build();

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

        when(userRepositoryMock.getUserById(request.getOwnerId())).thenReturn(Optional.of(user1));
        when(userRepositoryMock.getUserById(request.getCustomerId())).thenReturn(Optional.of(user2));
        when(propertyRepositoryMock.getPropertyById(request.getPropertyId())).thenReturn(Optional.of(property1));
        //Act
        createOrderUseCase.createOrder(request);
        //Assert
        verify(orderRepositoryMock).createOrder(request);
    }
}
