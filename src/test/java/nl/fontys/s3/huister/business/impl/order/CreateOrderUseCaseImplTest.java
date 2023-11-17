package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.price.PriceMustBeMoreThanZeroException;
import nl.fontys.s3.huister.business.exception.property.PropertyNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
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
class CreateOrderUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    /**
     * @verifies throw UserNotFoundException when owner or customer is not in the repository
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldThrowUserNotFoundExceptionWhenOwnerIsNotInTheRepository() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .duration(4)
                .price(300)
                .ownerId(1L)
                .propertyId(1L)
                .build();

        when(userRepositoryMock.findById(request.getOwnerId())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->createOrderUseCase.createOrder(request));

    }

    /**
     * @verifies throw PropertyNotFoundException when property is not found in the repository
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldThrowPropertyNotFoundExceptionWhenPropertyIsNotFoundInTheRepository() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .duration(4)
                .price(300)
                .ownerId(1L)
                .propertyId(1L)
                .build();

        UserEntity owner= UserEntity.builder().id(1L).build();
        UserEntity customer= UserEntity.builder().id(2L).build();

        when(requestAccessTokenMock.getId()).thenReturn(2L);
        when(userRepositoryMock.findById(request.getOwnerId())).thenReturn(Optional.of(owner));
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(customer));
        when(propertyRepositoryMock.findById(request.getPropertyId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(PropertyNotFoundException.class,()->createOrderUseCase.createOrder(request));
    }

    /**
     * @verifies throw PriceMustBeMoreThanZeroException when price is equals or below zero
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldThrowPriceMustBeMoreThanZeroExceptionWhenPriceIsEqualsOrBelowZero() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .duration(4)
                .price(0)
                .ownerId(1L)
                .propertyId(1L)
                .build();

        UserEntity owner= UserEntity.builder().id(1L).build();
        UserEntity customer= UserEntity.builder().id(2L).build();

        PropertyEntity property=PropertyEntity.builder().id(2L).build();

        when(requestAccessTokenMock.getId()).thenReturn(2L);
        when(userRepositoryMock.findById(request.getOwnerId())).thenReturn(Optional.of(owner));
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(customer));
        when(propertyRepositoryMock.findById(request.getPropertyId())).thenReturn(Optional.of(property));

        //Act + Assert
        assertThrows(PriceMustBeMoreThanZeroException.class,()->createOrderUseCase.createOrder(request));
    }

    /**
     * @verifies create new order when request is valid
     * @see CreateOrderUseCaseImpl#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldCreateNewOrderWhenRequestIsValid() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .duration(4)
                .price(370)
                .ownerId(1L)
                .propertyId(1L)
                .build();

        UserEntity owner= UserEntity.builder().id(1L).build();
        UserEntity customer= UserEntity.builder().id(2L).build();

        PropertyEntity property=PropertyEntity.builder().id(1L).build();

        when(requestAccessTokenMock.getId()).thenReturn(2L);
        when(userRepositoryMock.findById(request.getOwnerId())).thenReturn(Optional.of(owner));
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(customer));
        when(propertyRepositoryMock.findById(request.getPropertyId())).thenReturn(Optional.of(property));

        OrderEntity order=OrderEntity.builder()
                .property(property)
                .owner(owner)
                .status(OrderStatus.CREATED)
                .duration(request.getDuration())
                .customer(customer)
                .price(request.getPrice())
                .build();

        //Act
        createOrderUseCase.createOrder(request);

        //Assert
        verify(orderRepositoryMock).save(order);

    }

    /**
     * @verifies throw UserNotFoundException when customer is not in the repository
     * @see CreateOrderUseCaseImpl#createOrder(CreateOrderRequest)
     */
    @Test
    void createOrder_shouldThrowUserNotFoundExceptionWhenCustomerIsNotInTheRepository() {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .duration(4)
                .price(300)
                .ownerId(1L)
                .propertyId(1L)
                .build();

        UserEntity owner= UserEntity.builder().id(1L).build();

        when(requestAccessTokenMock.getId()).thenReturn(2L);
        when(userRepositoryMock.findById(request.getOwnerId())).thenReturn(Optional.of(owner));
        when(userRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->createOrderUseCase.createOrder(request));
    }
}
