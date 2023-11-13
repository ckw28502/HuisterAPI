package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.exception.user.UnauthorizedUserException;
import nl.fontys.s3.huister.business.exception.utilities.InvalidOperationException;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderUseCaseImplTest {
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private UpdateOrderUseCaseImpl updateOrderUseCase;

    /**
     * @verifies throw an OrderNotFoundException when order is not found
     * @see UpdateOrderUseCaseImpl#updateOrder(nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldThrowAnOrderNotFoundExceptionWhenOrderIsNotFound() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .build();

        when(orderRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(OrderNotFoundException.class,()->updateOrderUseCase.updateOrder(request));

    }

    /**
     * @verifies update the order when order is found
     * @see UpdateOrderUseCaseImpl#updateOrder(nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldUpdateTheOrderWhenOrderIsFound() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        UserEntity customer=UserEntity.builder()
                .id(2L)
                .role(UserRole.CUSTOMER)
                .build();

        OrderEntity order=OrderEntity.builder()
                .id(1L)
                .customer(customer)
                .owner(owner)
                .status(OrderStatus.CREATED)
                .build();

        when(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(order));

        when(requestAccessTokenMock.getRole()).thenReturn(UserRole.OWNER);
        when(requestAccessTokenMock.getId()).thenReturn(1L);

        //Act
        updateOrderUseCase.updateOrder(request);

        //Assert
        verify(orderRepositoryMock).updateOrder(request.getId(),request.getStatus());

    }

    /**
     * @verifies throw an InvalidOperationException when order is already accepted
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldThrowAnInvalidOperationExceptionWhenOrderIsAlreadyAccepted() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        UserEntity customer=UserEntity.builder()
                .id(2L)
                .role(UserRole.CUSTOMER)
                .build();

        OrderEntity order=OrderEntity.builder()
                .id(1L)
                .customer(customer)
                .owner(owner)
                .status(OrderStatus.ACCEPTED)
                .build();

        when(orderRepositoryMock.findById(request.getId())).thenReturn(Optional.of(order));

        //Act + Assert
        assertThrows(InvalidOperationException.class,()->updateOrderUseCase.updateOrder(request));
    }

    /**
     * @verifies throw an InvalidOperationException when new status is created
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldThrowAnInvalidOperationExceptionWhenNewStatusIsCreated() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(OrderStatus.CREATED)
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        UserEntity customer=UserEntity.builder()
                .id(2L)
                .role(UserRole.CUSTOMER)
                .build();

        OrderEntity order=OrderEntity.builder()
                .id(1L)
                .customer(customer)
                .owner(owner)
                .status(OrderStatus.CREATED)
                .build();

        when(orderRepositoryMock.findById(request.getId())).thenReturn(Optional.of(order));

        //Act + Assert
        assertThrows(InvalidOperationException.class,()->updateOrderUseCase.updateOrder(request));
    }

    /**
     * @verifies throw an UnauthorizedUserException when user does not have authority for the new status
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @ParameterizedTest
    @ValueSource(strings = {"REJECTED","ACCEPTED","CANCELLED"})
    void updateOrder_shouldThrowAnUnauthorizedUserExceptionWhenUserDoesNotHaveAuthorityForTheNewStatus(OrderStatus status) {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(status)
                .build();

        UserEntity owner=UserEntity.builder()
                .id(1L)
                .role(UserRole.OWNER)
                .build();

        UserEntity customer=UserEntity.builder()
                .id(2L)
                .role(UserRole.CUSTOMER)
                .build();

        OrderEntity order=OrderEntity.builder()
                .id(1L)
                .customer(customer)
                .owner(owner)
                .status(OrderStatus.CREATED)
                .build();

        when(orderRepositoryMock.findById(request.getId())).thenReturn(Optional.of(order));

        //Act + Assert
        assertThrows(UnauthorizedUserException.class,()->updateOrderUseCase.updateOrder(request));
    }
}
