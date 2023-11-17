package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.UpdateUserRequest;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
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
class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private AccessToken requestAccessTokenMock;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see UpdateUserUseCaseImpl#updateUser(nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    void updateUser_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder()
                .phoneNumber("0987654321")
                .name("resu")
                .build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->updateUserUseCase.updateUser(request));

    }

    /**
     * @verifies update user when user is found
     * @see UpdateUserUseCaseImpl#updateUser(nl.fontys.s3.huister.business.request.user.UpdateUserRequest)
     */
    @Test
    void updateUser_shouldUpdateUserWhenUserIsFound() {
        //Arrange
        UpdateUserRequest request=UpdateUserRequest.builder()
                .phoneNumber("0987654321")
                .name("resu")
                .build();

        UserEntity user=UserEntity.builder().build();

        when(requestAccessTokenMock.getId()).thenReturn(1L);
        when(userRepositoryMock.findById(requestAccessTokenMock.getId())).thenReturn(Optional.of(user));

        //Act
        updateUserUseCase.updateUser(request);

        //Assert
        verify(userRepositoryMock).updateUser(request.getName(),request.getPhoneNumber(),1L);

    }
}
