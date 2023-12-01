package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.RefreshTokenRequest;
import nl.fontys.s3.huister.business.response.user.RefreshTokenResponse;

public interface RefreshTokenUseCase {
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
