package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.UpdateUserUseCase;
import nl.fontys.s3.huister.domain.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param request new user data from client
     *
     * @should throw UserNotFoundException when user is not found
     * @should update user when user is found
     */
    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<User>user=userRepository.getUserById(request.getId());
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }
        userRepository.updateUser(request);
    }
}
