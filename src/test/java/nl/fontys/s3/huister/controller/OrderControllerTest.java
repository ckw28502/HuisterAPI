package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import nl.fontys.s3.huister.configuration.utilities.GsonConfig;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@Import(GsonConfig.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAllOrdersUseCase getAllOrdersUseCaseMock;
    @MockBean
    private CreateOrderUseCase createOrderUseCaseMock;
    @MockBean
    private UpdateOrderUseCase updateOrderUseCaseMock;

    @Autowired
    private Gson gson;


    /**
     * @verifies return an empty list when no order is found
     * @see OrderController#getAllOrders(long)
     */
    @Test
    void getAllOrders_shouldReturnAnEmptyListWhenNoOrderIsFound() throws Exception {
        //Arrange
        List<GetAllOrdersResponse>responses=List.of();

        when(getAllOrdersUseCaseMock.getAllOrders(1)).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(responses)));

    }

    /**
     * @verifies return a list of orders when orders are found
     * @see OrderController#getAllOrders(long)
     */
    @Test
    void getAllOrders_shouldReturnAListOfOrdersWhenOrdersAreFound() throws Exception {
        //Arrange

        GetAllOrdersResponse response1=GetAllOrdersResponse.builder()
                .endRent(LocalDate.now().toString())
                .streetName("property1 street")
                .price(800)
                .imageUrl("property1Image.png")
                .cityName("city1")
                .status(OrderStatus.ACCEPTED)
                .build();

        GetAllOrdersResponse response2=GetAllOrdersResponse.builder()
                .endRent(LocalDate.now().toString())
                .streetName("property2 street")
                .price(500.52)
                .imageUrl("property2Image.png")
                .cityName("city2")
                .status(OrderStatus.ACCEPTED)
                .build();

        List<GetAllOrdersResponse>responses=List.of(response1,response2);

        when(getAllOrdersUseCaseMock.getAllOrders(1)).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(header().string("content-type",APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(responses)));
    }

    /**
     * @verifies return a response with created status
     * @see OrderController#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldReturnAResponseWithCreatedStatus() throws Exception {
        //Arrange
        CreateOrderRequest request=CreateOrderRequest.builder()
                .customerId(1L)
                .ownerId(2)
                .propertyId(1)
                .duration(2)
                .price(600.95)
                .build();

        //Act + Assert
        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(createOrderUseCaseMock).createOrder(request);

    }

    /**
     * @verifies return a no content response
     * @see OrderController#updateOrder(long, nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldReturnANoContentResponse() throws Exception {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .build();

        //Act + Assert
        mockMvc.perform(put("/orders/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateOrderUseCaseMock).updateOrder(request);

    }
}
