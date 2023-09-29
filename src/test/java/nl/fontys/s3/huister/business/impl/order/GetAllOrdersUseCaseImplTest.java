package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
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
public class GetAllOrdersUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
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
                .phoneNumber("0123456789")
                .build();

        OrderEntity order= OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        GetAllOrdersResponse expectedResponse=GetAllOrdersResponse.builder()
                .orders(List.of(order))
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(orderRepositoryMock.getAllOrder(user.getId())).thenReturn(List.of(order));

        //Act
        GetAllOrdersResponse actualResponse=getAllOrdersUseCase.getAllOrders(user.getId());
        //Assert
        assertEquals(expectedResponse,actualResponse);
    }
}
