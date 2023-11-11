package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.business.response.property.GetAllPropertiesResponse;

import java.util.List;

public interface GetAllPropertiesUseCase {
    List<GetAllPropertiesResponse>getAllProperties();
}
