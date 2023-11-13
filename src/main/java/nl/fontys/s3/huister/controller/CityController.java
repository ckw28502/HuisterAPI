package nl.fontys.s3.huister.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:4173"})
public class CityController {
    private GetAllCitiesUseCase getAllCitiesUseCase;

    /**
     *
     * @return list of cities response
     *
     * @should return 401 with unauthorized user
     * @should return 200 with logged-in user
     */
    @RolesAllowed({"ADMIN","OWNER","CUSTOMER"})
    @GetMapping()
    ResponseEntity<GetAllCitiesResponse>getAllCities(){
        return ResponseEntity.ok(getAllCitiesUseCase.getAllCities());
    }
}
