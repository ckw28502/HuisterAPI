package nl.fontys.s3.huister.business.interfaces.city;

import nl.fontys.s3.huister.domain.request.City.CreateCityRequest;

public interface CreateCityUseCase {
    void createCity(final CreateCityRequest request);
}
