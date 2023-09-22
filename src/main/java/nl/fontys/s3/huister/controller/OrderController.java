package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.order.CreateOrderUseCase;
import nl.fontys.s3.huister.domain.request.order.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<Void>createOrder(
            @RequestBody@Valid CreateOrderRequest request){
        createOrderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
