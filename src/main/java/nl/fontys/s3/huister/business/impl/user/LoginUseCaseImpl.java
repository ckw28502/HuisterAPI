package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.AccountHasNotBeenActivatedException;
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

    /**
     *
     * @param request contains username and password for login
     * @return user id and name
     *
     * @should throw UserNotFoundException when failed to find user with username equals to request's username
     * @should throw InvalidPasswordException when request's password and found user's password are different
     * @should throw AccountHasNotBeenActivatedException when credentials are correct but the account has not been activated
     * @should return response filled with user id and name if user is found and request's password is correct
     */
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

        if(!user.isActivated()){
            throw new AccountHasNotBeenActivatedException();
        }

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
