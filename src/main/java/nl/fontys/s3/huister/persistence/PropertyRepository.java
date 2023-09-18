package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.Property;

import java.util.List;

public interface PropertyRepository {
    List<Property>getAllNotRentedProperties();

    List<Property> getAllProperties();
    List<Property>getPropertiesByOwner(int id);
}
