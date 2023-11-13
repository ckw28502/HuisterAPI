package nl.fontys.s3.huister.controller;

import com.google.gson.Gson;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @MockBean
    private GetAllOrdersUseCase getAllOrdersUseCase;
    @MockBean
    private CreateOrderUseCase createOrderUseCase;
    @MockBean
    private UpdateOrderUseCase updateOrderUseCase;

    /**
     * @verifies return 401 when user is not logged-in
     * @see OrderController#getAllOrders()
     */
    @Test
    void getAllOrders_shouldReturn401WhenUserIsNotLoggedin() throws Exception {
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * @verifies return 403 when user is admin
     * @see OrderController#getAllOrders()
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllOrders_shouldReturn403WhenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 200 when user is authorized
     * @see OrderController#getAllOrders()
     */
    @Test
    @WithMockUser(roles = {"OWNER","CUSTOMER"})
    void getAllOrders_shouldReturn200WhenUserIsAuthorized() throws Exception {
        //Arrange
        List<GetAllOrdersResponse>responses=List.of(GetAllOrdersResponse.builder().build());

        when(getAllOrdersUseCase.getAllOrders()).thenReturn(responses);

        //Act + Assert
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(responses)));

    }

    /**
     * @verifies return 401 when user is not logged-in
     * @see OrderController#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    void createOrder_shouldReturn401WhenUserIsNotLoggedin() throws Exception {
        //Arrange
        CreateOrderRequest request= CreateOrderRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/orders")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /**
     * @verifies return 403 when user is unauthorized
     * @see OrderController#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    @WithMockUser(roles = {"ADMIN","OWNER"})
    void createOrder_shouldReturn403WhenUserIsUnauthorized() throws Exception {
        //Arrange
        CreateOrderRequest request= CreateOrderRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 201 when user is customer
     * @see OrderController#createOrder(nl.fontys.s3.huister.business.request.order.CreateOrderRequest)
     */
    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createOrder_shouldReturn201WhenUserIsCustomer() throws Exception {
        //Arrange
        CreateOrderRequest request= CreateOrderRequest.builder().build();

        //Act + Assert
        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(createOrderUseCase).createOrder(request);
    }

    /**
     * @verifies return 401 when user is not logged-in
     * @see OrderController#updateOrder(long, nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    void updateOrder_shouldReturn401WhenUserIsNotLoggedin() throws Exception {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/orders/1")
                .contentType(APPLICATION_JSON_VALUE)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /**
     * @verifies return 403 when user is admin
     * @see OrderController#updateOrder(long, nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateOrder_shouldReturn403WhenUserIsAdmin() throws Exception {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/orders/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * @verifies return 204 when user is authorized
     * @see OrderController#updateOrder(long, nl.fontys.s3.huister.business.request.order.UpdateOrderRequest)
     */
    @Test
    @WithMockUser(roles = {"OWNER","CUSTOMER"})
    void updateOrder_shouldReturn204WhenUserIsAuthorized() throws Exception {
        //Arrange
        UpdateOrderRequest request=UpdateOrderRequest.builder()
                .id(1L)
                .build();

        //Act + Assert
        mockMvc.perform(put("/orders/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateOrderUseCase).updateOrder(request);
    }
}
