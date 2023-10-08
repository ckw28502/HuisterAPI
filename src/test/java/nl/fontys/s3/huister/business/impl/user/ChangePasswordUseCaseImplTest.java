package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangePasswordUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private ChangePasswordUseCaseImpl changePasswordUseCase;
    /**
     * @verifies throw new UserNotFoundException when user cannot be found
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    public void changePassword_shouldThrowNewUserNotFoundExceptionWhenUserCannotBeFound() {
        //Arrange
        ChangePasswordRequest request=ChangePasswordRequest.builder()
                .id(1)
                .newPassword("user1")
                .build();
        when(userRepositoryMock.getUserById(request.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->changePasswordUseCase.changePassword(request));
    }

    /**
     * @verifies not replace the old password if the new password matches the old password
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    public void changePassword_shouldNotReplaceTheOldPasswordIfTheNewPasswordMatchesTheOldPassword() {
        //Arrange
        ChangePasswordRequest request=ChangePasswordRequest.builder()
                .id(1)
                .newPassword("user1")
                .build();

        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        when(userRepositoryMock.getUserById(request.getId())).thenReturn(Optional.of(user));
        //Act
        changePasswordUseCase.changePassword(request);
        //Assert
        verify(userRepositoryMock,never()).changePassword(request.getId(),request.getNewPassword());
    }

    /**
     * @verifies replace the old password with the new password if both passwords isn't a match
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    public void changePassword_shouldReplaceTheOldPasswordWithTheNewPasswordIfBothPasswordsIsntAMatch() {
        //Arrange
        ChangePasswordRequest request=ChangePasswordRequest.builder()
                .id(1)
                .newPassword("user2")
                .build();

        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        when(userRepositoryMock.getUserById(request.getId())).thenReturn(Optional.of(user));
        //Act
        changePasswordUseCase.changePassword(request);
        //Assert
        verify(userRepositoryMock).changePassword(request.getId(),request.getNewPassword());
    }
}
