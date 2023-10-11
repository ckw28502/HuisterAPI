package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.interfaces.user.GetAllOwnersUseCase;
import nl.fontys.s3.huister.business.response.user.GetAllOwnersResponse;
import nl.fontys.s3.huister.domain.entities.UserEntity;
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

    @Override
    public List<GetAllOwnersResponse> getAllOwners() {
        List<UserEntity>owners=userRepository.getAllOwners();

        return owners.stream().map(owner->GetAllOwnersResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .propertyOwned(propertyRepository.getPropertiesCount(owner.getId()))
                .propertyRented(orderRepository.getAllAcceptedOrdersForOwner(owner.getId()).size())
                .build()).toList();
    }
}
