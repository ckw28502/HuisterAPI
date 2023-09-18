package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.persistence.entity.PropertyEntity;

import java.util.List;

public interface PropertyRepository {
    List<PropertyEntity>getAllPropertiesForCustomer();
}
