package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.property.GetAllPropertiesUseCase;
import nl.fontys.s3.huister.business.property.GetPropertyDetailUseCase;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.domain.response.property.GetPropertyDetailResponse;
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
    private final GetPropertyDetailUseCase getPropertyDetailUseCase;

    @GetMapping("{id}")
    public ResponseEntity<List<GetAllPropertiesResponse>>getAllPropertiesForCustomer(
            @PathVariable(value = "id")int id){
        return ResponseEntity.ok(getAllPropertiesUseCase.GetAllProperties(id));
    }
    @GetMapping("detail/{id}")
    public ResponseEntity<GetPropertyDetailResponse>getPropertyDetail(
            @PathVariable(value = "id")final int id){
        return ResponseEntity.ok(getPropertyDetailUseCase.getPropertyDetail(id));
    }
}
