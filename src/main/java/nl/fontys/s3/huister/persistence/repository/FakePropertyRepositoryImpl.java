package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakePropertyRepositoryImpl implements PropertyRepository {
    private final List<PropertyEntity>properties;

    public FakePropertyRepositoryImpl() {
        this.properties = new ArrayList<>();
    }

    @Override
    public List<PropertyEntity> getAllPropertiesForCustomer() {
        List<PropertyEntity> filteredProperties=properties.stream().filter(property ->
                !property.isRented()
        ).toList();
        return Collections.unmodifiableList(filteredProperties);
    }
}
