package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.InvalidPasswordException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.LoginUseCase;
import nl.fontys.s3.huister.business.request.user.LoginRequest;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;

    @Override
    public LoginResponse Login(LoginRequest request) {
        Optional<UserEntity>optionalUser=userRepository.getUserByUsername(request.getUsername());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity user=optionalUser.get();
        if (!user.getPassword().equals(request.getPassword())){
            throw new InvalidPasswordException();
        }
        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
