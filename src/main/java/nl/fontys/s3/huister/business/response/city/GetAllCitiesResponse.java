package nl.fontys.s3.huister.business.response.city;

import lombok.*;
import nl.fontys.s3.huister.domain.entities.CityEntity;

import java.util.List;
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class GetAllCitiesResponse {
    private List<CityEntity>cities;
}
