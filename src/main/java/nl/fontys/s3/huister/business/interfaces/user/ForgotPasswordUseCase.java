package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.ForgotPasswordRequest;

public interface ForgotPasswordUseCase {
    void forgotPassword(ForgotPasswordRequest request);
}
