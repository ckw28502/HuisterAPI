package nl.fontys.s3.huister.business.impl.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.AccountHasNotBeenActivatedException;
import nl.fontys.s3.huister.business.exception.user.InvalidPasswordException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.LoginUseCase;
import nl.fontys.s3.huister.business.request.user.LoginRequest;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.huister.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    /**
     *
     * @param request login request
     * @return login response
     *
     * @should throw UserNotFoundException when username is invalid
     * @should throw InvalidPasswordException when password is wrong
     * @should throw AccountHasNotBeenActivatedException when account is not activated
     * @should return response when credentials are valid
     */
    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Optional<UserEntity>optionalUser=userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        UserEntity user=optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidPasswordException();
        }

        if(!user.isActivated()){
            throw new AccountHasNotBeenActivatedException();
        }

        String token=accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getId(), user.getRole(), user.getProfilePictureUrl())
        );

        userRepository.saveToken(token,user.getUsername());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
