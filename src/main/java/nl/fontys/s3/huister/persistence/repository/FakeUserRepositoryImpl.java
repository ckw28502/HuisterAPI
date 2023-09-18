package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.Model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeUserRepositoryImpl implements UserRepository {
    private static int NEXT_ID=1;
    private final List<User>users;

    public FakeUserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users.stream().filter(user->user.getId()==id).findFirst();
    }
}
