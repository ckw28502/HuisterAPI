package nl.fontys.s3.huister.business.response.property;

import lombok.*;

@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
public class GetRentedNotRentedPropertyRatioResponse {
        private int rented;
        private int notRented;
}
