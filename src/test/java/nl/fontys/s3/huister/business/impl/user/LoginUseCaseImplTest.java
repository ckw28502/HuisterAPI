package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.AccountHasNotBeenActivatedException;
import nl.fontys.s3.huister.business.exception.user.InvalidPasswordException;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.LoginRequest;
import nl.fontys.s3.huister.business.response.user.LoginResponse;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    /**
     * @verifies throw UserNotFoundException when username is invalid
     * @see LoginUseCaseImpl#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldThrowUserNotFoundExceptionWhenUsernameIsInvalid() {
        //Arrange
        LoginRequest request=LoginRequest.builder()
                .username("user")
                .password("user")
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->loginUseCase.login(request));
    }

    /**
     * @verifies throw InvalidPasswordException when password is wrong
     * @see LoginUseCaseImpl#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldThrowInvalidPasswordExceptionWhenPasswordIsWrong() {
        //Arrange
        LoginRequest request=LoginRequest.builder()
                .username("user")
                .password("user")
                .build();

        UserEntity user=UserEntity.builder()
                .id(1L)
                .profilePictureUrl("user.jpg")
                .role(UserRole.OWNER)
                .password("resu")
                .activated(false)
                .name("user")
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        //Act + Assert
        assertThrows(InvalidPasswordException.class,()->loginUseCase.login(request));
    }

    /**
     * @verifies throw AccountHasNotBeenActivatedException when account is not activated
     * @see LoginUseCaseImpl#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldThrowAccountHasNotBeenActivatedExceptionWhenAccountIsNotActivated() {
        //Arrange
        LoginRequest request=LoginRequest.builder()
                .username("user")
                .password("user")
                .build();

        UserEntity user=UserEntity.builder()
                .id(1L)
                .profilePictureUrl("user.jpg")
                .role(UserRole.OWNER)
                .password("user")
                .activated(false)
                .name("user")
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        //Act + Assert
        assertThrows(AccountHasNotBeenActivatedException.class,()->loginUseCase.login(request));
    }

    /**
     * @verifies return response when credentials are valid
     * @see LoginUseCaseImpl#login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    void login_shouldReturnResponseWhenCredentialsAreValid() {
        //Arrange
        LoginRequest request=LoginRequest.builder()
                .username("user")
                .password("user")
                .build();

        UserEntity user=UserEntity.builder()
                .id(1L)
                .profilePictureUrl("user.jpg")
                .role(UserRole.OWNER)
                .password("user")
                .activated(true)
                .name("user")
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        LoginResponse expectedResponse=LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();

        //Act
        LoginResponse actualResponse=loginUseCase.login(request);

        //Assert
        assertEquals(expectedResponse,actualResponse);

    }
}
