package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllOwnersUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private GetAllOwnersUseCaseImpl getAllOwnersUseCase;

    /**
     * @verifies return an empty list if there is no owner
     * @see GetAllOwnersUseCaseImpl#getAllOwners()
     */
    @Test
    void getAllOwners_shouldReturnAnEmptyListIfThereIsNoOwner() {
        //Arrange
        when(userRepositoryMock.findAllByRole(UserRole.OWNER)).thenReturn(List.of());

        //Act
        List<GetAllOwnersResponse>responses=getAllOwnersUseCase.getAllOwners();

        //Assert
        assert responses.isEmpty();

    }

    /**
     * @verifies return list of owners if there are owners
     * @see GetAllOwnersUseCaseImpl#getAllOwners()
     */
    @Test
    void getAllOwners_shouldReturnListOfOwnersIfThereAreOwners() {
        //Arrange
        UserEntity owner=UserEntity.builder()
                .id(1L)
                .name("owner")
                .email("owner")
                .profilePictureUrl("owner.png")
                .build();

        when(userRepositoryMock.findAllByRole(UserRole.OWNER)).thenReturn(List.of(owner));

        GetAllOwnersResponse expectedResponse=GetAllOwnersResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .profilePictureUrl(owner.getProfilePictureUrl())
                .propertyOwned(0)
                .propertyRented(0)
                .build();

        when(propertyRepositoryMock.countByOwner(owner)).thenReturn(0);
        when(orderRepositoryMock.countByOwnerAndStatus(owner,OrderStatus.ACCEPTED)).thenReturn(0);

        //Act
        List<GetAllOwnersResponse>actualResponses=getAllOwnersUseCase.getAllOwners();

        //Assert
        assertEquals(List.of(expectedResponse),actualResponses);

    }
}
