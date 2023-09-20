package nl.fontys.s3.huister.domain.response.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.huister.Model.City;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllCitiesResponse {
    private List<City>cities;
}
