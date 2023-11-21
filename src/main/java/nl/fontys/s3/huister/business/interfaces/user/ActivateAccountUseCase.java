package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.ActivateAccountRequest;

public interface ActivateAccountUseCase {
    void activateAccount(ActivateAccountRequest request);
}
