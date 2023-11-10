package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeOrderRepositoryImpl implements OrderRepository {
    private final List<OrderEntity>orders;
    private int NEXT_ID=1;

    public FakeOrderRepositoryImpl() {
        this.orders=new ArrayList<>();
    }

    @Override
    public void createOrder(CreateOrderRequest request) {
        orders.add(OrderEntity.builder()
                .id(NEXT_ID)
                .propertyId(request.getPropertyId())
                .ownerId(request.getOwnerId())
                .status(OrderStatus.CREATED)
                .duration(request.getDuration())
                .customerId(request.getCustomerId())
                .price(request.getPrice())
                .build());
        NEXT_ID++;
    }

    @Override
    public OrderEntity updateOrder(UpdateOrderRequest request) {
        Optional<OrderEntity> optionalOrder=orders.stream().filter(order -> order.getId()==request.getId()).findFirst();
        if (optionalOrder.isPresent()){
            OrderEntity updatedOrder=optionalOrder.get();
            updatedOrder.setStatus(request.getStatus());
            Collections.fill(orders,updatedOrder);
            return updatedOrder;
        }
        return null;
    }

    @Override
    public List<OrderEntity> getAllOrder(int userId) {
        return orders.stream().filter(order ->
                userId== order.getOwnerId()||userId== order.getCustomerId())
                .toList();
    }

    @Override
    public List<OrderEntity> getAllAcceptedOrdersForOwner(int userId) {
        return this.orders.stream().filter(order->userId==order.getOwnerId()&&order.getStatus().equals(OrderStatus.ACCEPTED)).toList();
    }

    @Override
    public boolean doesOrderExists(int id) {
        return orders.stream().anyMatch(order -> order.getId()==id);
    }
}
