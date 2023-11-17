package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
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
class GetUserByIdUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private GetUserByIdUseCaseImpl getUserByIdUseCase;

    /**
     * @verifies throw UserNotFoundException when user not found
     * @see GetUserByIdUseCaseImpl#getUserById(long)
     */
    @Test
    void getUserById_shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        //Arrange
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //Act + Assert
        assertThrows(UserNotFoundException.class,()->getUserByIdUseCase.getUserById(1L));

    }

    /**
     * @verifies return user when user is found
     * @see GetUserByIdUseCaseImpl#getUserById(long)
     */
    @Test
    void getUserById_shouldReturnUserWhenUserIsFound() {
        //Arrange
        UserEntity user=UserEntity.builder()
                .id(1L)
                .name("user")
                .email("user.email.com")
                .phoneNumber("0123456789")
                .username("user")
                .role(UserRole.OWNER)
                .profilePictureUrl("user.jpg")
                .build();

        when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));

        GetUserByIdResponse expectedResponse=GetUserByIdResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();

        //Act
        GetUserByIdResponse actualResponse=getUserByIdUseCase.getUserById(user.getId());

        //Assert
        assertEquals(expectedResponse,actualResponse);

    }
}
