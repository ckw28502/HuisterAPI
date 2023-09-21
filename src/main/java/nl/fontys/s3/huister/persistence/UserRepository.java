package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;
import nl.fontys.s3.huister.domain.request.user.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(final int id);
    boolean usernameExist(String username);
    void createUser(CreateUserRequest request);
    void updateUser(UpdateUserRequest request);
    List<User>getAllUser();
}
