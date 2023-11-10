package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.entities.CityEntity;

import java.util.List;
import java.util.Optional;

public interface CityRepository {
    Optional<CityEntity>getCityById(final int id);
    Optional<CityEntity>getCityByName(final String name);
    List<CityEntity>getAllCities();
    List<CityEntity>getAllCities(List<Integer>propertyCityIds);
    boolean cityNameExists(String name);
    int createCity(String name);
    void deleteCity(final int id);
}
