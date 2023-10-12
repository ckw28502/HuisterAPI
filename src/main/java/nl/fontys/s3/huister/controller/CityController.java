package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.business.response.city.GetAllCitiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CityController {
    private GetAllCitiesUseCase getAllCitiesUseCase;

    @GetMapping("{id}")
    ResponseEntity<GetAllCitiesResponse>getAllCities(@PathVariable(value = "id")int userId){
        return ResponseEntity.ok(getAllCitiesUseCase.getAllCities(userId));
    }
}
