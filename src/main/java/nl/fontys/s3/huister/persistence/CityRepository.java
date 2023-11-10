package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity,Long> {
    /**
     *
     * @param id city id
     * @return optional city
     *
     * @should return empty optional when city is not found
     * @should return city when city is found
     */
    Optional<CityEntity>findById(final long id);
    @NonNull
    List<CityEntity> findAll();
    @Query("SELECT DISTINCT c FROM CityEntity c JOIN PropertyEntity p ON c.id=p.city.id WHERE p.owner.id=:id")
    List<CityEntity> findByOwnerId(@Param("id") final long id);
    @Query("SELECT DISTINCT c FROM CityEntity c JOIN PropertyEntity p ON c.id=p.city.id WHERE p.endRent IS NULL")
    List<CityEntity> findCityByEndRentIsNull();

}
