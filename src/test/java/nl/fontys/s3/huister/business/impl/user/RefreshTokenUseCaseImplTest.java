package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.InvalidTokenException;
import nl.fontys.s3.huister.business.request.user.RefreshTokenRequest;
import nl.fontys.s3.huister.business.response.user.RefreshTokenResponse;
import nl.fontys.s3.huister.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.huister.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseImplTest {
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private RefreshTokenUseCaseImpl refreshTokenUseCase;
    /**
     * @verifies throw InvalidUserException when token is not exists
     * @see RefreshTokenUseCaseImpl#refreshToken(nl.fontys.s3.huister.business.request.user.RefreshTokenRequest)
     */
    @Test
    void refreshToken_shouldThrowInvalidUserExceptionWhenTokenIsNotExists() {
        //Arrange
        RefreshTokenRequest request=RefreshTokenRequest.builder().token("old token").build();

        when(userRepositoryMock.findByToken(request.getToken())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(InvalidTokenException.class,()->refreshTokenUseCase.refreshToken(request));

    }

    /**
     * @verifies return new AccessToken when old token exists
     * @see RefreshTokenUseCaseImpl#refreshToken(nl.fontys.s3.huister.business.request.user.RefreshTokenRequest)
     */
    @Test
    void refreshToken_shouldReturnNewAccessTokenWhenOldTokenExists() {
        //Arrange
        RefreshTokenRequest request=RefreshTokenRequest.builder().token("old token").build();
        UserEntity user=UserEntity.builder().username("user").token(request.getToken()).build();

        when(userRepositoryMock.findByToken(request.getToken())).thenReturn(Optional.of(user));
        when(accessTokenEncoder.encode(new AccessTokenImpl(user.getUsername(), user.getId(), user.getRole(), user.getProfilePictureUrl())))
                .thenReturn("new token");

        RefreshTokenResponse expectedResponse=RefreshTokenResponse.builder().token("new token").build();

        //Act
        RefreshTokenResponse actualResponse=refreshTokenUseCase.refreshToken(request);

        //Assert
        assertEquals(expectedResponse,actualResponse);
        verify(userRepositoryMock).saveToken(expectedResponse.getToken(),user.getUsername());
    }
}
