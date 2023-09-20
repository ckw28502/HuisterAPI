package nl.fontys.s3.huister.business.interfaces.property;

import nl.fontys.s3.huister.domain.response.property.GetPropertyDetailResponse;

public interface GetPropertyDetailUseCase {
    GetPropertyDetailResponse getPropertyDetail(final int id);
}
