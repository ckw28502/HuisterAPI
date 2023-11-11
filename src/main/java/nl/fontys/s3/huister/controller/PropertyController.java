package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.property.*;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;
import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;
import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173"})
public class PropertyController {
    private final GetAllPropertiesUseCase getAllPropertiesUseCase;
    private final GetPropertyDetailUseCase getPropertyDetailUseCase;
    private final CreatePropertyUseCase createPropertyUseCase;
    private final UpdatePropertyUseCase updatePropertyUseCase;
    private final DeletePropertyUseCase deletePropertyUseCase;
    private final GetRentedNotRentedPropertyRatioUseCase getRentedNotRentedPropertyRatioUseCase;

    /**
     *
     * @return list of properties
     *
     * @should return an empty list content if no properties found
     * @should return list of properties content if properties are found
     */
    @Secured({"ADMIN","OWNER","CUSTOMER"})
    @GetMapping()
    public ResponseEntity<List<GetAllPropertiesResponse>>getAllProperties(){
        return ResponseEntity.ok(getAllPropertiesUseCase.getAllProperties());
    }

    /**
     *
     * @param id property id
     * @return property
     *
     * @should return property content
     */
    @Secured({"ADMIN","OWNER","CUSTOMER"})
    @GetMapping("detail/{id}")
    public ResponseEntity<GetPropertyDetailResponse>getPropertyDetail(
            @PathVariable(value = "id")final long id){
        return ResponseEntity.ok(getPropertyDetailUseCase.getPropertyDetail(id));
    }

    /**
     *
     * @return rented property and not rented property count
     *
     * @should return a valid response
     */
    @Secured({"ADMIN","OWNER"})
    @GetMapping("dashboard/rentedRatio")
    public ResponseEntity<GetRentedNotRentedPropertyRatioResponse>getRentedNotRentedPropertyRatio(){
        return ResponseEntity.ok(getRentedNotRentedPropertyRatioUseCase.getRentedNotRentedPropertyRatio());
    }

    /**
     *
     * @param request create property request
     * @return http created response
     *
     * @should create property
     *
     */
    @Secured("OWNER")
    @PostMapping
    public ResponseEntity<Integer>createProperty(@RequestBody @Valid CreatePropertyRequest request){
        createPropertyUseCase.createProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param request update property request
     * @param id property id
     * @return http response with no content status
     *
     * @should update property
     *
     */
    @Secured({"OWNER"})
    @PutMapping("{id}")
    public ResponseEntity<Void>updateProperty(
            @RequestBody@Valid UpdatePropertyRequest request,
            @PathVariable(value = "id")final long id){
        request.setId(id);
        updatePropertyUseCase.updateProperty(request);
        return ResponseEntity.noContent().build();
    }


    /**
     *
     * @param id property id
     * @return http response with no content status
     *
     * @should delete property
     */
    @Secured({"OWNER"})
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deleteProperty(@PathVariable(value = "id")final long id){
        deletePropertyUseCase.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
