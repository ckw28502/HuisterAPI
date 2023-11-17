package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UsernameExistException;
import nl.fontys.s3.huister.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param request contains new user data
     *
     * @should throw UsernameExistException when username exists
     * @should create user when username is unique
     */
    @Override
    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistException();
        }
        UserEntity newUser=UserEntity.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .profilePictureUrl(request.getProfilePictureUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .email(request.getEmail())
                .activated(false)
                .build();
        userRepository.save(newUser);
    }
}
