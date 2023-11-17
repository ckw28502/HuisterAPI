package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.UpdateUserUseCase;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    /**
     *
     * @param request new user data from client
     *
     * @should throw UserNotFoundException when user is not found
     * @should update user when user is found
     */
    @Override
    @Transactional
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity>user=userRepository.findById(requestAccessToken.getId());
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        userRepository.updateUser(request.getName(),request.getPhoneNumber(),requestAccessToken.getId());
    }
}
