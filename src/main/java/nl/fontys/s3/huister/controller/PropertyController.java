package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.GetAllPropertiesForCustomerUseCase;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final GetAllPropertiesForCustomerUseCase getAllPropertiesForCustomerUseCase;

    @GetMapping("/customer")
    public ResponseEntity<List<GetAllPropertiesResponse>>getAllPropertiesForCustomer(){
        return ResponseEntity.ok(getAllPropertiesForCustomerUseCase.GetAllPropertiesForCustomer());
    }
}
