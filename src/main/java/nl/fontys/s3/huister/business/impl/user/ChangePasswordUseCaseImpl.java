package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.ChangePasswordUseCase;
import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param request filled with user id and new password
     *
     * @should throw new UserNotFoundException when user cannot be found
     * @should not replace the old password if the new password matches the old password
     * @should replace the old password with the new password if both passwords isn't a match
     */
    @Override
    public void changePassword(ChangePasswordRequest request) {
        Optional<UserEntity>optionalUser=userRepository.getUserById(request.getId());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        UserEntity user=optionalUser.get();
        if (!user.getPassword().equals(request.getNewPassword())){
            userRepository.changePassword(request.getId(), request.getNewPassword());
        }
    }
}
