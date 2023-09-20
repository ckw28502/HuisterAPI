package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.domain.request.property.UpdatePropertyRequest;

public interface UpdatePropertyUseCase {
    void updateProperty(UpdatePropertyRequest request);
}
