package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.business.interfaces.order.GetAllOrdersUseCase;
import nl.fontys.s3.huister.business.interfaces.order.UpdateOrderUseCase;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.business.response.order.GetAllOrdersResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173"})
public class OrderController {
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;

    /**
     *
     * @param userId user id
     * @return HTTP response with list of order content
     *
     * @should return an empty list when no order is found
     * @should return a list of orders when orders are found
     */
    @GetMapping("{userId}")
    public ResponseEntity<List<GetAllOrdersResponse>>getAllOrders(
            @PathVariable(value = "userId")long userId
    ){
        return ResponseEntity.ok(getAllOrdersUseCase.getAllOrders(userId));
    }

    /**
     *
     * @param request request for creating new order
     * @return HTTP response with created status
     *
     * @should return a response with created status
     */

    @PostMapping
    public ResponseEntity<Void>createOrder(
            @RequestBody@Valid CreateOrderRequest request){
        createOrderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param id order id
     * @param request new order value
     * @return HTTP response with no content status
     *
     * @should return a no content response
     */
    @PutMapping("{id}")
    public ResponseEntity<Void>updateOrder(
            @PathVariable(value = "id")final long id,
            @RequestBody@Valid UpdateOrderRequest request
            ){
        request.setId(id);
        updateOrderUseCase.updateOrder(request);
        return ResponseEntity.noContent().build();
    }
}
