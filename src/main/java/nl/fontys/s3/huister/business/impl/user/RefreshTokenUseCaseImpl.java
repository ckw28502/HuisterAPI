package nl.fontys.s3.huister.business.impl.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.InvalidTokenException;
import nl.fontys.s3.huister.business.interfaces.user.RefreshTokenUseCase;
import nl.fontys.s3.huister.business.request.user.RefreshTokenRequest;
import nl.fontys.s3.huister.business.response.user.RefreshTokenResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.huister.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {
    private UserRepository userRepository;
    private final AccessTokenEncoder accessTokenEncoder;


    /**
     *
     * @param request Refresh Token request
     * @return Refresh Token Response
     *
     * @should throw InvalidUserException when token is not exists
     * @should return new AccessToken when old token exists
     */
    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        Optional<UserEntity> optionalUser=userRepository.findByToken(request.getToken());
        if (optionalUser.isEmpty()){
            throw new InvalidTokenException();
        }

        UserEntity user=optionalUser.get();

        String newToken=accessTokenEncoder.encode(
                new AccessTokenImpl(user.getUsername(), user.getId(), user.getRole(), user.getProfilePictureUrl())
        );

        userRepository.saveToken(newToken,user.getUsername());

        return RefreshTokenResponse.builder()
                .token(newToken)
                .build();
    }
}
