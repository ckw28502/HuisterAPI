package nl.fontys.s3.huister.business.interfaces.city;

import nl.fontys.s3.huister.domain.response.city.GetAllCitiesResponse;

public interface GetAllCitiesUseCase {
    GetAllCitiesResponse getAllCities(int userId);
}