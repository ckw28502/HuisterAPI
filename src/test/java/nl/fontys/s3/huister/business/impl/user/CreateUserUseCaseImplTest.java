package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UsernameExistException;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {
    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;


    /**
     * @verifies throw UsernameExistException when username exists
     * @see CreateUserUseCaseImpl#createUser(nl.fontys.s3.huister.business.request.user.CreateUserRequest)
     */
    @Test
    void createUser_shouldThrowUsernameExistExceptionWhenUsernameExists() {
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder()
                .username("user")
                .profilePictureUrl("user.jpg")
                .email("user@email.com")
                .name("user")
                .password("user")
                .phoneNumber("0123456789")
                .role(UserRole.OWNER)
                .build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(true);

        //Act + Assert
        assertThrows(UsernameExistException.class,()->createUserUseCase.createUser(request));

    }

    /**
     * @verifies create user when username is unique
     * @see CreateUserUseCaseImpl#createUser(nl.fontys.s3.huister.business.request.user.CreateUserRequest)
     */
    @Test
    void createUser_shouldCreateUserWhenUsernameIsUnique() {
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder()
                .username("user")
                .profilePictureUrl("user.jpg")
                .email("user@email.com")
                .name("user")
                .password("user")
                .phoneNumber("0123456789")
                .role(UserRole.OWNER)
                .build();

        when(userRepositoryMock.existsByUsername(request.getUsername())).thenReturn(false);

        UserEntity user=UserEntity.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .profilePictureUrl(request.getProfilePictureUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .email(request.getEmail())
                .activated(false)
                .build();

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("ACTIVATE YOUR HUISTER ACCOUNT!!!");
        message.setText("Click this link to activate your account!\n" +
                "http://localhost:5173/activate/"+request.getUsername());

        //Act
        createUserUseCase.createUser(request);

        //Assert
        verify(userRepositoryMock).save(user);
        verify(javaMailSender).send(message);

    }
}
