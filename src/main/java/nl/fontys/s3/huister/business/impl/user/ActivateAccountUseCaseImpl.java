package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.ActivateAccountUseCase;
import nl.fontys.s3.huister.business.request.user.ActivateAccountRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ActivateAccountUseCaseImpl implements ActivateAccountUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param request ActivateAccountRequest
     *
     * @should throw UserNotFoundException if user is not found
     * @should activate user account when user is found
     *
     */
    @Override
    @Transactional
    public void activateAccount(ActivateAccountRequest request) {
        String username=request.getUsername();
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException();
        }
        userRepository.activateAccount(username);
    }
}
