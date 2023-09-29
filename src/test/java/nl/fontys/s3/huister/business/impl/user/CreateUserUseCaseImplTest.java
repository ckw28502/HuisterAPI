package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UsernameExistException;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.model.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    /**
     * @verifies throw exception when username not unique
     * @see CreateUserUseCaseImpl#createUser(CreateUserRequest)
     */
    @Test
    public void createUser_shouldThrowExceptionWhenUsernameNotUnique() {
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder()
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();
        when(userRepositoryMock.usernameExist(request.getUsername())).thenReturn(true);
        //Act + Assert
        assertThrows(UsernameExistException.class,()->createUserUseCase.createUser(request));
    }

    /**
     * @verifies add new repository when username is unique
     * @see CreateUserUseCaseImpl#createUser(CreateUserRequest)
     */
    @Test
    public void createUser_shouldAddNewRepositoryWhenUsernameIsUnique(){
        //Arrange
        CreateUserRequest request=CreateUserRequest.builder()
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();
        //Act
        createUserUseCase.createUser(request);
        //Assert
        verify(userRepositoryMock).createUser(request);
    }
}
