package nl.fontys.s3.huister.business.interfaces.user;

import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;

public interface CreateUserUseCase {
    void createUser(CreateUserRequest request);
}
