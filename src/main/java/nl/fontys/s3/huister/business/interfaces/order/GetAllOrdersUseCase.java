package nl.fontys.s3.huister.business.interfaces.order;

import nl.fontys.s3.huister.domain.response.order.GetAllOrdersResponse;

public interface GetAllOrdersUseCase {
    GetAllOrdersResponse getAllOrders(int userId);
}
