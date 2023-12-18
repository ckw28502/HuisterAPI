package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.ChangePasswordUseCase;
import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChangePasswordUseCaseImpl implements ChangePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     *
     * @param request filled with user id and new password
     *
     * @should throw new UserNotFoundException when user is not found
     * @should not replace the old password if the new password matches the old password
     * @should replace the old password if the new password does not match the old password
     */
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        //check if user is found
        Optional<UserEntity>optionalUser=userRepository.findByUsername(request.getUsername());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        //if old password and new password are different, change the password
        UserEntity user=optionalUser.get();
        if (!passwordEncoder.matches(request.getNewPassword(),user.getPassword())){
            userRepository.setPassword(user.getId(), passwordEncoder.encode(request.getNewPassword()));
        }
    }
}
