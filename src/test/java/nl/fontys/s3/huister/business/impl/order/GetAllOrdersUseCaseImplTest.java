package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
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
class GetAllOrdersUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private GetAllOrdersUseCaseImpl getAllOrdersUseCase;

    /**
     * @verifies throw an UserNotFoundException when user is not found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(long)
     */
    @Test
    void getAllOrders_shouldThrowAnUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getAllOrdersUseCase.getAllOrders(1L));

    }

    /**
     * @verifies return list of orders when user is found
     * @see GetAllOrdersUseCaseImpl#getAllOrders(long)
     */
    @Test
    void getAllOrders_shouldReturnListOfOrdersWhenUserIsFound() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1L)
                .build();

        CityEntity city= CityEntity.builder()
                .name("city")
                .build();

        PropertyEntity property=PropertyEntity.builder()
                .imageUrl("property.png")
                .streetName("property street")
                .endRent(LocalDate.now())
                .city(city)
                .build();

        List<OrderEntity>orders=List.of(OrderEntity.builder()
                .id(1L)
                .price(395)
                .property(property)
                .status(OrderStatus.CREATED)
                .build());

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.findAllByOwnerOrCustomer(user,user)).thenReturn(orders);

        List<GetAllOrdersResponse>expectedResponses=orders.stream().map(order ->GetAllOrdersResponse.builder()
                .cityName(order.getProperty().getCity().getName())
                .price(order.getPrice())
                .imageUrl(order.getProperty().getImageUrl())
                .streetName(order.getProperty().getStreetName())
                .endRent(order.getProperty().getEndRent().toString())
                .status(order.getStatus())
                .build()
        ).toList();

        //Act
        List<GetAllOrdersResponse> actualResponses=getAllOrdersUseCase.getAllOrders(user.getId());

        //Assert
        assertEquals(expectedResponses,actualResponses);
    }

    /**
     * @verifies return an empty list when there is no appropriate order
     * @see GetAllOrdersUseCaseImpl#getAllOrders(long)
     */
    @Test
    void getAllOrders_shouldReturnAnEmptyListWhenThereIsNoAppropriateOrder() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1L)
                .build();

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.findAllByOwnerOrCustomer(user,user)).thenReturn(List.of());

        //Act
        List<GetAllOrdersResponse> responses=getAllOrdersUseCase.getAllOrders(user.getId());

        //Assert
        assert responses.isEmpty();

    }
}
