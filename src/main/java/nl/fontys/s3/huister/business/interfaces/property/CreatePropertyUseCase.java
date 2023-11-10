package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;

public interface CreatePropertyUseCase {
    int createProperty(CreatePropertyRequest request);
}
