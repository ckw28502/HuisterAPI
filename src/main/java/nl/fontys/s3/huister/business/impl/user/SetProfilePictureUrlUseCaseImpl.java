package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UsernameDoesNotExistException;
import nl.fontys.s3.huister.business.interfaces.user.SetProfilePictureUrlUseCase;
import nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetProfilePictureUrlUseCaseImpl implements SetProfilePictureUrlUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param request set profile picture request
     *
     * @should throw UsernameDoesNotExistException when user is not found
     * @should set profile picture url
     */
    @Override
    public void setProfilePictureUrl(SetProfilePictureUrlRequest request) {
        if (!userRepository.existsByUsername(request.getUsername())){
            throw new UsernameDoesNotExistException();
        }
        userRepository.setProfilePictureUrl(request.getUsername(),request.getProfilePictureUrl());
    }
}
