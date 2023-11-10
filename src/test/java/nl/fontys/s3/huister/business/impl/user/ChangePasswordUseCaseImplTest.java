package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.ChangePasswordRequest;
import nl.fontys.s3.huister.domain.entities.UserEntity;
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
class ChangePasswordUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private ChangePasswordUseCaseImpl changePasswordUseCase;

    /**
     * @verifies throw new UserNotFoundException when user is not found
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    void changePassword_shouldThrowNewUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        ChangePasswordRequest request= ChangePasswordRequest.builder()
                .id(1L)
                .newPassword("password").build();
        when(userRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->changePasswordUseCase.changePassword(request));
    }

    /**
     * @verifies not replace the old password if the new password matches the old password
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    void changePassword_shouldNotReplaceTheOldPasswordIfTheNewPasswordMatchesTheOldPassword() {
        //Arrange
        ChangePasswordRequest request= ChangePasswordRequest.builder()
                .id(1L)
                .newPassword("password").build();

        UserEntity user= UserEntity.builder()
                .password("password")
                .build();
        when(userRepositoryMock.findById(request.getId())).thenReturn(Optional.of(user));

        //Act
        changePasswordUseCase.changePassword(request);

        //Assert
        verify(userRepositoryMock,never()).setPassword(request.getId(),request.getNewPassword());
    }

    /**
     * @verifies replace the old password if the new password does not match the old password
     * @see ChangePasswordUseCaseImpl#changePassword(nl.fontys.s3.huister.business.request.user.ChangePasswordRequest)
     */
    @Test
    void changePassword_shouldReplaceTheOldPasswordIfTheNewPasswordDoesNotMatchTheOldPassword() {
        //Arrange
        ChangePasswordRequest request= ChangePasswordRequest.builder()
                .id(1L)
                .newPassword("new password").build();

        UserEntity user= UserEntity.builder()
                .password("old password")
                .build();
        when(userRepositoryMock.findById(request.getId())).thenReturn(Optional.of(user));

        //Act
        changePasswordUseCase.changePassword(request);

        //Assert
        verify(userRepositoryMock).setPassword(request.getId(),request.getNewPassword());
    }
}
