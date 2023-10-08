package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;

public interface ChangePasswordUseCase {
    void changePassword(ChangePasswordRequest request);
}
