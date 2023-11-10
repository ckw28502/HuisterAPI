package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderUseCaseImplTest {
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @InjectMocks
    private UpdateOrderUseCaseImpl updateOrderUseCase;
    /**
     * @verifies throw an OrderNotFoundException when order is not found
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @Test
    public void updateOrder_shouldThrowAnOrderNotFoundExceptionWhenOrderIsNotFound() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1)
                .status(OrderStatus.ACCEPTED)
                .build();
        when(orderRepositoryMock.doesOrderExists(request.getId())).thenReturn(false);
        //Act + Assert
        assertThrows(OrderNotFoundException.class,()->updateOrderUseCase.updateOrder(request));
    }

    /**
     * @verifies update the order when order is found
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @Test
    public void updateOrder_shouldUpdateTheOrderWhenOrderIsFound() {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1)
                .status(OrderStatus.REJECTED)
                .build();
        when(orderRepositoryMock.doesOrderExists(request.getId())).thenReturn(true);
        //Act
        updateOrderUseCase.updateOrder(request);
        //Assert
        verify(orderRepositoryMock).updateOrder(request);
    }

    /**
     * @verifies change property status to rented if the new order status is accepted
     * @see UpdateOrderUseCaseImpl#updateOrder(UpdateOrderRequest)
     */
    @Test
    public void updateOrder_shouldChangePropertyStatusToRentedIfTheNewOrderStatusIsAccepted()  {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1)
                .status(OrderStatus.ACCEPTED)
                .build();

        OrderEntity order= OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build();

        when(orderRepositoryMock.doesOrderExists(request.getId())).thenReturn(true);
        when(orderRepositoryMock.updateOrder(request)).thenReturn(order);
        //Act
        updateOrderUseCase.updateOrder(request);
        //Assert
        verify(orderRepositoryMock).updateOrder(request);
        verify(propertyRepositoryMock).rentProperty(order);
    }
}
