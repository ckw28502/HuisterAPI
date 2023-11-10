package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.LoginRequest;
import nl.fontys.s3.huister.business.response.user.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
