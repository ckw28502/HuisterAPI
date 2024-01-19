package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.response.property.GetPropertyDetailResponse;

public interface CreatePropertyUseCase {
    GetPropertyDetailResponse createProperty(CreatePropertyRequest request);
}
