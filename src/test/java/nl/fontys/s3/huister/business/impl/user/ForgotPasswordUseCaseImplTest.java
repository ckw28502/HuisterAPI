package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.request.user.ForgotPasswordRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private ForgotPasswordUseCaseImpl forgotPasswordUseCase;

    /**
     * @verifies throw UserNotFoundException when user is not found
     * @see ForgotPasswordUseCaseImpl#forgotPassword(nl.fontys.s3.huister.business.request.user.ForgotPasswordRequest)
     */
    @Test
    void forgotPassword_shouldThrowUserNotFoundExceptionWhenUserIsNotFound() {
        //Arrange
        ForgotPasswordRequest request=ForgotPasswordRequest.builder()
                .username("user")
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->forgotPasswordUseCase.forgotPassword(request));

    }

    /**
     * @verifies send an email if user is found
     * @see ForgotPasswordUseCaseImpl#forgotPassword(nl.fontys.s3.huister.business.request.user.ForgotPasswordRequest)
     */
    @Test
    void forgotPassword_shouldSendAnEmailIfUserIsFound() {
        //Arrange
        ForgotPasswordRequest request=ForgotPasswordRequest.builder()
                .username("user")
                .build();

        UserEntity user=UserEntity.builder()
                .username(request.getUsername())
                .build();

        when(userRepositoryMock.findByUsername(request.getUsername())).thenReturn(Optional.of(user));

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("CREATE NEW PASSWORD");
        message.setText("Click this link below to create new password\n" +
                "http://localhost:5173/change/"+user.getUsername());

        //Act
        forgotPasswordUseCase.forgotPassword(request);

        //Assert
        verify(mailSender).send(message);

    }
}
