package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.Model.City;
import nl.fontys.s3.huister.domain.request.City.CreateCityRequest;
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
    public boolean cityNameExists(String name) {
        return cities.stream().anyMatch(city->name.equalsIgnoreCase(city.getName()));
    }

    @Override
    public void createCity(CreateCityRequest request) {
        cities.add(City.builder()
                .id(NEXT_ID)
                .name(request.getName())
                .build());
        NEXT_ID++;
    }

    @Override
    public void deleteCity(int id) {
        cities.removeIf(city -> city.getId()==id);
    }
}
