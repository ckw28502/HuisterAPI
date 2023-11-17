package nl.fontys.s3.huister.controller;

import jakarta.annotation.security.RolesAllowed;
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
     * @return HTTP response with list of order content
     *
     * @should return 401 when user is not logged-in
     * @should return 403 when user is admin
     * @should return 200 when user is authorized
     */
    @RolesAllowed({"OWNER","CUSTOMER"})
    @GetMapping()
    public ResponseEntity<List<GetAllOrdersResponse>>getAllOrders(){
        return ResponseEntity.ok(getAllOrdersUseCase.getAllOrders());
    }

    /**
     *
     * @param request request for creating new order
     * @return HTTP response with created status
     *
     * @should return 401 when user is not logged-in
     * @should return 403 when user is unauthorized
     * @should return 201 when user is customer
     *
     */

    @RolesAllowed("CUSTOMER")
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
     * @should return 401 when user is not logged-in
     * @should return 403 when user is admin
     * @should return 204 when user is authorized
     *
     */
    @RolesAllowed({"OWNER","CUSTOMER"})
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
