package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.property.UpdatePropertyRequest;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository {
    Optional<PropertyEntity> getPropertyById(final int id);
    List<PropertyEntity>getAllNotRentedProperties();

    List<PropertyEntity> getAllProperties();
    List<PropertyEntity>getPropertiesByOwner(int id);
    boolean isCityHasNoProperty(int id);
    void createProperty(CreatePropertyRequest request);
    void updateProperty(UpdatePropertyRequest request);
    void deleteProperty(final int id);
}
