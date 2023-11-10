package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.business.response.property.GetRentedNotRentedPropertyRatioResponse;

public interface GetRentedNotRentedPropertyRatioUseCase {
    GetRentedNotRentedPropertyRatioResponse getRentedNotRentedPropertyRatio(int userId);
}
