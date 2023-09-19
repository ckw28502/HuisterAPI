package nl.fontys.s3.huister.business.property;

import nl.fontys.s3.huister.domain.request.property.CreatePropertyRequest;

public interface CreatePropertyUseCase {
    void createProperty(CreatePropertyRequest request);
}
