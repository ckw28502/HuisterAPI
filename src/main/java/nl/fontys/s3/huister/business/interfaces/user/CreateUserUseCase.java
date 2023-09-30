package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.business.request.user.CreateUserRequest;

public interface CreateUserUseCase {
    void createUser(CreateUserRequest request);
}
