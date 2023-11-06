package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UsernameDoesNotExistException;
import nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest;
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
class SetProfilePictureUrlUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private SetProfilePictureUrlUseCaseImpl setProfilePictureUrlUseCase;

    /**
     * @verifies throw UsernameDoesNotExistException when user is not found
     * @see SetProfilePictureUrlUseCaseImpl#setProfilePictureUrl(nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest)
     */
    @Test
    void setProfilePictureUrl_shouldThrowUsernameDoesNotExistExceptionWhenUserIsNotFound() {
        //Arrange
        SetProfilePictureUrlRequest request=SetProfilePictureUrlRequest.builder().build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(false);

        //Act + Assert
        assertThrows(UsernameDoesNotExistException.class,()->setProfilePictureUrlUseCase.setProfilePictureUrl(request));

    }

    /**
     * @verifies set profile picture url
     * @see SetProfilePictureUrlUseCaseImpl#setProfilePictureUrl(nl.fontys.s3.huister.business.request.user.SetProfilePictureUrlRequest)
     */
    @Test
    void setProfilePictureUrl_shouldSetProfilePictureUrl() {
        //Arrange
        SetProfilePictureUrlRequest request=SetProfilePictureUrlRequest.builder().build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(true);

        //Act
        setProfilePictureUrlUseCase.setProfilePictureUrl(request);

        //Assert
        verify(userRepositoryMock).setProfilePictureUrl(request.getUsername(),request.getProfilePictureUrl());

    }
}
