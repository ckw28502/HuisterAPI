package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.domain.request.City.CreateCityRequest;

import java.util.Optional;

public interface CityRepository {
    Optional<City>getCityById(final int id);
    boolean cityNameExists(String name);
    void createCity(CreateCityRequest request);
}
