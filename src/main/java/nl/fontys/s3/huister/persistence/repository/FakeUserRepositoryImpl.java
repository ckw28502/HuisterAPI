package nl.fontys.s3.huister.persistence.repository;

import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeUserRepositoryImpl implements UserRepository {
    private static int NEXT_ID=1;
    private final List<UserEntity>users;

    public FakeUserRepositoryImpl() {
        this.users = new ArrayList<>();
    }

    @Override
    public Optional<UserEntity> getUserById(int id) {
        return users.stream().filter(user->user.getId()==id).findFirst();
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return this.users.stream().filter((user)->user.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public boolean usernameExist(String username) {
        return users.stream().anyMatch(user -> username.equalsIgnoreCase(user.getUsername()));
    }

    @Override
    public void createUser(CreateUserRequest request) {
        users.add(UserEntity.builder()
                .id(NEXT_ID)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .profilePictureUrl(request.getProfilePictureUrl())
                .password(request.getPassword())
                .username(request.getUsername())
                .activated(false)
                .build());
        NEXT_ID+=1;
        System.out.println(NEXT_ID);
    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        UserEntity user=getUserById(request.getId()).get();
        int index=users.indexOf(user);
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setProfilePictureUrl(request.getProfilePictureUrl());
        users.set(index,user);
    }
}
