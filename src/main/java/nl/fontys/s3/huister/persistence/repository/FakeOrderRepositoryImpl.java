package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.model.Order;
import nl.fontys.s3.huister.model.OrderStatus;
import nl.fontys.s3.huister.persistence.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeOrderRepositoryImpl implements OrderRepository {
    private final List<Order>orders;
    private static int NEXT_ID=1;

    public FakeOrderRepositoryImpl() {
        this.orders=new ArrayList<>();
    }

    @Override
    public void createOrder(CreateOrderRequest request) {
        orders.add(Order.builder()
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
    public void     updateOrder(UpdateOrderRequest request) {
        Order updatedOrder=orders.stream().filter(order -> order.getId()==request.getId()).findFirst().get();
        updatedOrder.setStatus(request.getStatus());
        Collections.fill(orders,updatedOrder);
    }

    @Override
    public List<Order> getAllOrder(int userId) {
        return orders.stream().filter(order ->
                userId== order.getOwnerId()||userId== order.getCustomerId())
                .toList();
    }

    @Override
    public boolean doesOrderExists(int id) {
        return orders.stream().anyMatch(order -> order.getId()==id);
    }
}
