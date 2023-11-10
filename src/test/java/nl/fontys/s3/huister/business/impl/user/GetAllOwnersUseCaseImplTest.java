package nl.fontys.s3.huister.business.impl.user;

import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllOwnersUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PropertyRepository propertyRepositoryMock;
    @Mock
    private OrderRepository orderRepositoryMock;
    @InjectMocks
    private GetAllOwnersUseCaseImpl getAllOwnersUseCase;
    /**
     * @verifies return list of owners
     * @see GetAllOwnersUseCaseImpl#getAllOwners()
     */
    @Test
    public void getAllOwners_shouldReturnListOfOwners() {
        //Arrange
        UserEntity user= UserEntity.builder()
                .id(1)
                .username("user1")
                .role(UserRole.OWNER)
                .profilePictureUrl("Image.png")
                .password("user1")
                .name("user1")
                .email("user1@gmail.com")
                .phoneNumber("0123456789")
                .activated(true)
                .build();

        List<OrderEntity>orders=List.of(OrderEntity.builder()
                .id(1)
                .ownerId(1)
                .customerId(2)
                .duration(8)
                .propertyId(1)
                .price(500)
                .build());

        List<PropertyEntity>properties=List.of(PropertyEntity.builder()
                .id(1)
                .ownerId(1)
                .cityId(1)
                .streetName("street")
                .description("Good Place")
                .postCode("1111AA")
                .price(600)
                .imageUrls(List.of("image1.png","image2.png"))
                .isRented(true)
                .area(10)
                .endRent(LocalDate.now())
                .build());

        when(userRepositoryMock.getAllOwners()).thenReturn(List.of(user));
        when(propertyRepositoryMock.getPropertiesCount(user.getId())).thenReturn(properties.size());
        when(orderRepositoryMock.getAllAcceptedOrdersForOwner(user.getId())).thenReturn(orders);

        List<GetAllOwnersResponse>expectedResponses=List.of(GetAllOwnersResponse.builder()
                .id(user.getId())
                .profilePictureUrl(user.getProfilePictureUrl())
                .name(user.getName())
                .email(user.getEmail())
                .propertyOwned(properties.size())
                .propertyRented(orders.size())
                .build());
        //Act
        List<GetAllOwnersResponse>actualResponses=getAllOwnersUseCase.getAllOwners();
        //Assert
        assertEquals(expectedResponses,actualResponses);
    }
}
