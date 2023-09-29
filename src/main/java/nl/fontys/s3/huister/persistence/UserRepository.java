package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> getUserById(final int id);
    boolean usernameExist(String username);
    void createUser(CreateUserRequest request);
    void updateUser(UpdateUserRequest request);
}
