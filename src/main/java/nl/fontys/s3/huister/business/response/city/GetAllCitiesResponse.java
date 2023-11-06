package nl.fontys.s3.huister.business.response.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.huister.domain.entities.CityEntity;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class GetAllCitiesResponse {
    private List<CityEntity>cities;
}
