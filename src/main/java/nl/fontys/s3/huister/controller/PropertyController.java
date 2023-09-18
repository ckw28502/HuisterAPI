package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.property.GetAllPropertiesUseCase;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final GetAllPropertiesUseCase getAllPropertiesUseCase;

    @GetMapping("{userId}")
    public ResponseEntity<List<GetAllPropertiesResponse>>getAllPropertiesForCustomer(
            @PathVariable(value = "userId")int userId){
        return ResponseEntity.ok(getAllPropertiesUseCase.GetAllProperties(userId));
    }
}
