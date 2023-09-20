package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.City;

import java.util.Optional;

public interface CityRepository {
    Optional<City>getCityById(final int id);
    Optional<City>getCityByName(final String name);
    boolean cityNameExists(String name);
    int createCity(String name);
    void deleteCity(final int id);
}
