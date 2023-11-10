package nl.fontys.s3.huister.business.response.property;

import lombok.*;

@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRentedNotRentedPropertyRatioResponse {
        public long rented,notRented;
}
