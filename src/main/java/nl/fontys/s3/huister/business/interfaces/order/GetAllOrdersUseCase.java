package nl.fontys.s3.huister.business.interfaces.order;

import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;

import java.util.List;

public interface GetAllOrdersUseCase {
    List<GetAllOrdersResponse> getAllOrders();
}
