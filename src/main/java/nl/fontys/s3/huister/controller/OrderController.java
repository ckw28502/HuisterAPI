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

    @GetMapping("{userId}")
    public ResponseEntity<List<GetAllOrdersResponse>>getAllOrders(
            @PathVariable(value = "userId")int userId
    ){
        return ResponseEntity.ok(getAllOrdersUseCase.getAllOrders(userId));
    }

    @PostMapping
    public ResponseEntity<Void>createOrder(
            @RequestBody@Valid CreateOrderRequest request){
        createOrderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("{id}")
    public ResponseEntity<Void>updateOrder(
            @PathVariable(value = "id")final int id,
            @RequestBody@Valid UpdateOrderRequest request
            ){
        request.setId(id);
        updateOrderUseCase.updateOrder(request);
        return ResponseEntity.noContent().build();
    }
}
