package nl.fontys.s3.huister.business.impl.order;

import nl.fontys.s3.huister.business.exception.order.OrderNotFoundException;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.model.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
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
                .status(OrderStatus.ACCEPTED)
                .build();
        when(orderRepositoryMock.doesOrderExists(request.getId())).thenReturn(true);
        //Act
        updateOrderUseCase.updateOrder(request);
        //Assert
        verify(orderRepositoryMock).updateOrder(request);
    }
}
