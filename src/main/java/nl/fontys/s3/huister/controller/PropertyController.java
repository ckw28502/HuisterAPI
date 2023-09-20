package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.property.*;
import nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.domain.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.domain.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.domain.response.property.GetPropertyDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final GetAllPropertiesUseCase getAllPropertiesUseCase;
    private final GetPropertyDetailUseCase getPropertyDetailUseCase;
    private final CreatePropertyUseCase createPropertyUseCase;
    private final UpdatePropertyUseCase updatePropertyUseCase;
    private final DeletePropertyUseCase deletePropertyUseCase;

    @GetMapping("{userId}")
    public ResponseEntity<List<GetAllPropertiesResponse>>getAllPropertiesForCustomer(
            @PathVariable(value = "userId")int userId){
        return ResponseEntity.ok(getAllPropertiesUseCase.GetAllProperties(userId));
    }
    @GetMapping("detail/{id}")
    public ResponseEntity<GetPropertyDetailResponse>getPropertyDetail(
            @PathVariable(value = "id")final int id){
        return ResponseEntity.ok(getPropertyDetailUseCase.getPropertyDetail(id));
    }
    @PostMapping
    public ResponseEntity<Void>createProperty(@RequestBody @Valid CreatePropertyRequest request){
        createPropertyUseCase.createProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("{id}")
    public ResponseEntity<Void>updateProperty(
            @RequestBody@Valid UpdatePropertyRequest request,
            @PathVariable(value = "id")final int id){
        request.setId(id);
        updatePropertyUseCase.updateProperty(request);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deleteProperty(@PathVariable(value = "id")final int id){
        deletePropertyUseCase.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
