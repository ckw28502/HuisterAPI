package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.user.GetAllOwnersUseCase;
import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllOwnersUseCaseImpl implements GetAllOwnersUseCase {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OrderRepository orderRepository;

    /**
     *
     * @return list of owners
     *
     * @should return an empty list if there is no owner
     * @should return list of owners if there are owners
     */
    @Override
    public List<GetAllOwnersResponse> getAllOwners() {
        List<UserEntity>owners=userRepository.findAllByRole(UserRole.OWNER);

        return owners.stream().map(owner->GetAllOwnersResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .profilePictureUrl(owner.getProfilePictureUrl())
                .propertyOwned(propertyRepository.countByOwner(owner))
                .propertyRented(orderRepository.countByOwnerAndStatus(owner, OrderStatus.ACCEPTED))
                .build()).toList();
    }
}
