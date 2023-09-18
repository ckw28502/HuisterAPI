package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.User;
import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;
import nl.fontys.s3.huister.domain.request.user.UpdateUserRequest;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(final int id);
    boolean usernameExist(String username);
    void createUser(CreateUserRequest request);
    void updateUser(UpdateUserRequest request);
}
