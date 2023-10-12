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
public class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    /**
     * @verifies throw UserNotFoundException when failed to find user with username equals to request's username
     * @see LoginUseCaseImpl#Login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    public void Login_shouldThrowUserNotFoundExceptionWhenFailedToFindUserWithUsernameEqualsToRequestsUsername(){
        //Arrange
        LoginRequest request=LoginRequest.builder()
                .username("user1")
                .password("user1")
                .build();

        when(userRepositoryMock.getUserByUsername(request.getUsername())).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->loginUseCase.Login(request));
    }

    /**
     * @verifies throw InvalidPasswordException when request's password and found user's password are different
     * @see LoginUseCaseImpl#Login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    public void Login_shouldThrowInvalidPasswordExceptionWhenRequestsPasswordAndFoundUsersPasswordAreDifferent(){
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        LoginRequest request=LoginRequest.builder()
                .username("user1")
                .password("user2")
                .build();

        when(userRepositoryMock.getUserByUsername(request.getUsername())).thenReturn(Optional.of(user));
        //Act + Assert
        assertThrows(InvalidPasswordException.class,()->loginUseCase.Login(request));
    }

    /**
     * @verifies return response filled with user id and name if user is found and request's password is correct
     * @see LoginUseCaseImpl#Login(nl.fontys.s3.huister.business.request.user.LoginRequest)
     */
    @Test
    public void Login_shouldReturnResponseFilledWithUserIdAndNameIfUserIsFoundAndRequestsPasswordIsCorrect(){
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .email("user1@gmail.com")
                .name("user1")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        LoginRequest request=LoginRequest.builder()
                .username("user1")
                .password("user1")
                .build();

        LoginResponse expectedResponse=LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();

        when(userRepositoryMock.getUserByUsername(request.getUsername())).thenReturn(Optional.of(user));
        //Act
        LoginResponse actualResponse=loginUseCase.Login(request);
        //Assert
        assertEquals(expectedResponse,actualResponse);
    }

    /**
     * @verifies throw AccountHasNotBeenActivatedException when credentials are correct but the account has not been activated
     * @see LoginUseCaseImpl#Login(LoginRequest)
     */
    @Test
    public void Login_shouldThrowAccountHasNotBeenActivatedExceptionWhenCredentialsAreCorrectButTheAccountHasNotBeenActivated() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(false)
                .build();

        LoginRequest request=LoginRequest.builder()
                .username("user1")
                .password("user1")
                .build();
        when(userRepositoryMock.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        //Act + Assert
        assertThrows(AccountHasNotBeenActivatedException.class,()->loginUseCase.Login(request));
    }
}
