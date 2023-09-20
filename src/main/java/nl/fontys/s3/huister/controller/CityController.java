package nl.fontys.s3.huister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.city.CreateCityUseCase;
import nl.fontys.s3.huister.domain.request.City.CreateCityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CreateCityUseCase createCityUseCase;
    @PostMapping
    public ResponseEntity<Void> createCity(@RequestBody @Valid CreateCityRequest request){
        createCityUseCase.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
