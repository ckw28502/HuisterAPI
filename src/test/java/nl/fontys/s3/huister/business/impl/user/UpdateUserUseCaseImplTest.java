package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.model.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;
    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see UpdateUserUseCaseImpl#updateUser(UpdateUserRequest)
     */
    @Test
    public void updateUser_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder()
                .id(1)
                .phoneNumber("0123456789")
                .profilePictureUrl("image.jpg")
                .name("user1")
                .build();
        when(userRepositoryMock.getUserById(1)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->updateUserUseCase.updateUser(request));
    }

    /**
     * @verifies update user when user is found
     * @see UpdateUserUseCaseImpl#updateUser(UpdateUserRequest)
     */
    @Test
    public void updateUser_shouldUpdateUserWhenUserIsFound() {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder()
                .id(1)
                .phoneNumber("0123456789")
                .profilePictureUrl("image.jpg")
                .name("user1")
                .build();
        User user=User.builder()
                .id(1)
                .phoneNumber("0123456789")
                .profilePictureUrl("image.jpg")
                .name("user1")
                .password("user1")
                .role(UserRole.OWNER)
                .username("user1")
                .build();

        when(userRepositoryMock.getUserById(1)).thenReturn(Optional.of(user));
        //Act
        updateUserUseCase.updateUser(request);
        //Assert
        verify(userRepositoryMock).updateUser(request);
    }
}
