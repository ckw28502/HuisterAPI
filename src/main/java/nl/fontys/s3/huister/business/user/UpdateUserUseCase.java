package nl.fontys.s3.huister.business.user;

import nl.fontys.s3.huister.domain.request.user.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
