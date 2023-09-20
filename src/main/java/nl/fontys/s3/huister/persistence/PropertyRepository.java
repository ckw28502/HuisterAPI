package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.Property;
import nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.domain.request.property.UpdatePropertyRequest;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository {
    Optional<Property> getPropertyById(final int id);
    List<Property>getAllNotRentedProperties();

    List<Property> getAllProperties();
    List<Property>getPropertiesByOwner(int id);
    void createProperty(CreatePropertyRequest request);
    void updateProperty(UpdatePropertyRequest request);
}
