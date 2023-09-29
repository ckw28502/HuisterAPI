package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.model.User;
import nl.fontys.s3.huister.model.UserRole;
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
public class GetUserByIdUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private GetUserByIdUseCaseImpl getUserByIdUseCase;

    /**
     * @verifies throw UserNotFoundException when user not found
     * @see GetUserByIdUseCaseImpl#getUserById(int)
     */
    @Test
    public void getUserById_shouldThrowUserNotFoundExceptionWhenUserNotFound(){
        //Arrange
        int id=1;
        when(userRepositoryMock.getUserById(id)).thenReturn(Optional.empty());
        //Act + Assert
        assertThrows(UserNotFoundException.class,()->
            getUserByIdUseCase.getUserById(id)
        );
    }

    /**
     * @verifies return GetUserByIdResponse containing found user
     * @see GetUserByIdUseCaseImpl#getUserById(int)
     */
    @Test
    public void getUserById_shouldReturnGetUserByIdResponseContainingFoundUser(){
        //Arrange
        User user=User.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .phoneNumber("0123456789")
                .build();

        GetUserByIdResponse expectedResponse=GetUserByIdResponse.builder()
                .id(1)
                .username(user.getUsername())
                .role(user.getRole())
                .profilePictureUrl(user.getProfilePictureUrl())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();

        when(userRepositoryMock.getUserById(user.getId())).thenReturn(Optional.of(user));
        //Act
        GetUserByIdResponse actualResponse=getUserByIdUseCase.getUserById(user.getId());
        //Assert
        assertEquals(expectedResponse,actualResponse);
    }
}
