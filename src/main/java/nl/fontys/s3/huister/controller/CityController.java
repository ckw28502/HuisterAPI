package nl.fontys.s3.huister.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.city.GetAllCitiesUseCase;
import nl.fontys.s3.huister.domain.response.city.GetAllCitiesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final GetAllCitiesUseCase getAllCitiesUseCase;
    @GetMapping("{userId}")
    public ResponseEntity<GetAllCitiesResponse>getAllCities(
            @PathVariable(value = "userId")final int userId){
        return ResponseEntity.ok(getAllCitiesUseCase.getAllCities(userId));
    }
}
