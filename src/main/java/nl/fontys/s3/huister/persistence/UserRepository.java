package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> getUserById(final int id);
    Optional<UserEntity> getUserByUsername(final String username);
    boolean usernameExist(String username);
    void changePassword(int id, String newPassword);
    void createUser(CreateUserRequest request);
    void updateUser(UpdateUserRequest request);
    void activateAccount(int id);
    List<UserEntity>getAllOwners();
}
