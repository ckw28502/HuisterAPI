package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.Model.Property;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakePropertyRepositoryImpl implements PropertyRepository {
    private final List<Property>properties;

    public FakePropertyRepositoryImpl() {
        this.properties = new ArrayList<>();
    }

    @Override
    public List<Property> getAllNotRentedProperties() {
        List<Property> notRentedProperties=this.properties.stream().filter(property ->
                !property.isRented()
        ).toList();
        return Collections.unmodifiableList(notRentedProperties);
    }

    @Override
    public List<Property> getAllProperties() {
        return Collections.unmodifiableList(this.properties);
    }

    @Override
    public List<Property> getPropertiesByOwner(int ownerId) {
        List<Property>ownedProperties= this.properties.stream().filter(property ->
                    property.getOwnerId()==ownerId
                ).toList();
        return Collections.unmodifiableList(ownedProperties);
    }
}
