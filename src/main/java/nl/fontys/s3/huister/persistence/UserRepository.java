package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.Model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(final int id);
}
