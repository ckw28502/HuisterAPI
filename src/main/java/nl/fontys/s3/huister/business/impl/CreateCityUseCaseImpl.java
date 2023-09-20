package nl.fontys.s3.huister.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.city.CityNameExistException;
import nl.fontys.s3.huister.business.interfaces.city.CreateCityUseCase;
import nl.fontys.s3.huister.domain.request.City.CreateCityRequest;
import nl.fontys.s3.huister.persistence.CityRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCityUseCaseImpl implements CreateCityUseCase {
    private final CityRepository cityRepository;
    @Override
    public void createCity(CreateCityRequest request) {
        if (cityRepository.cityNameExists(request.getName())){
            throw new CityNameExistException();
        }
        cityRepository.createCity(request);
    }
}
