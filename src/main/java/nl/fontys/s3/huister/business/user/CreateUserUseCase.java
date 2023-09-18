package nl.fontys.s3.huister.business.user;

import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;

public interface CreateUserUseCase {
    void createUser(CreateUserRequest request);
}
