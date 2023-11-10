package nl.fontys.s3.huister.business.response.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.domain.entities.CityEntity;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllCitiesResponse {
    private List<CityEntity>cities;
}
