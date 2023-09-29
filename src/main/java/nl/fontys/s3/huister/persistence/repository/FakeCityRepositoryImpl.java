package nl.fontys.s3.huister.persistence.repository;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.persistence.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FakeCityRepositoryImpl implements CityRepository {
    private static int NEXT_ID=1;
    private final List<CityEntity>cities;

    public FakeCityRepositoryImpl() {
        this.cities = new ArrayList<>();
    }
    @Override
    public Optional<CityEntity> getCityById(int id) {
        return cities.stream().filter(city -> city.getId()==id).findFirst();
    }

    @Override
    public Optional<CityEntity> getCityByName(String name) {
        return cities.stream().filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<CityEntity> getAllCities() {
        return Collections.unmodifiableList(cities);
    }

    @Override
    public List<CityEntity> getAllCities(List<Integer> propertyCityIds) {
        return Collections.unmodifiableList(cities.stream().filter(city ->
                propertyCityIds.contains(city.getId())).toList()
        );
    }

    @Override
    public boolean cityNameExists(String name) {
        return cities.stream().anyMatch(city->name.equalsIgnoreCase(city.getName()));
    }

    @Override
    public int createCity(String name) {
        cities.add(CityEntity.builder()
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
