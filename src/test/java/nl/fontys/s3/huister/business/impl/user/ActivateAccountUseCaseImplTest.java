package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.ActivateAccountRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivateAccountUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private ActivateAccountUseCaseImpl activateAccountUseCase;
    /**
     * @verifies throw UserNotFoundException if user is not found
     * @see ActivateAccountUseCaseImpl#activateAccount(nl.fontys.s3.huister.business.request.user.ActivateAccountRequest)
     */
    @Test
    void activateAccount_shouldThrowUserNotFoundExceptionIfUserIsNotFound() {
        //Arrange
        ActivateAccountRequest request=ActivateAccountRequest.builder()
                .username("user")
                .build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(false);

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->activateAccountUseCase.activateAccount(request));

    }

    /**
     * @verifies activate user account when user is found
     * @see ActivateAccountUseCaseImpl#activateAccount(nl.fontys.s3.huister.business.request.user.ActivateAccountRequest)
     */
    @Test
    void activateAccount_shouldActivateUserAccountWhenUserIsFound() {
        //Arrange
        ActivateAccountRequest request=ActivateAccountRequest.builder()
                .username("user")
                .build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(true);

        //Act
        activateAccountUseCase.activateAccount(request);

        //Assert
        verify(userRepositoryMock).activateAccount(request.getUsername());

    }
}
