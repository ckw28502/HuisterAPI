package nl.fontys.s3.huister.business.property;

import nl.fontys.s3.huister.domain.response.property.GetPropertyDetailResponse;

public interface GetPropertyDetailUseCase {
    GetPropertyDetailResponse getPropertyDetail(final int id);
}
