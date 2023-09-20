package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.persistence.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeCityRepositoryImpl implements CityRepository {
    private static int NEXT_ID=1;
    private final List<City>cities;

    public FakeCityRepositoryImpl() {
        this.cities = new ArrayList<>();
    }

    @Override
    public Optional<City> getCityById(int id) {
        return cities.stream().filter(city -> city.getId()==id).findFirst();
    }

    @Override
    public Optional<City> getCityByName(String name) {
        return cities.stream().filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean cityNameExists(String name) {
        return cities.stream().anyMatch(city->name.equalsIgnoreCase(city.getName()));
    }

    @Override
    public int createCity(String name) {
        cities.add(City.builder()
                .id(NEXT_ID)
                .name(name)
                .build());
        NEXT_ID++;
        return NEXT_ID-1;
    }

    @Override
    public void deleteCity(int id) {
        cities.removeIf(city -> city.getId()==id);
    }
}
