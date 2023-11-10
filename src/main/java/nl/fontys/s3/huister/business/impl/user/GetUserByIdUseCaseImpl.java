package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.GetUserByIdUseCase;
import nl.fontys.s3.huister.business.response.user.GetUserByIdResponse;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    private final UserRepository userRepository;

    /**
     *
     * @param id user id
     * @return user
     *
     * @should throw UserNotFoundException when user not found
     * @should return GetUserByIdResponse containing found user
     */
    @Override
    public GetUserByIdResponse getUserById(int id) {
        Optional<UserEntity>userOptional=userRepository.getUserById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        UserEntity user=userOptional.get();
        return GetUserByIdResponse.builder()
                .id(id)
                .name(user.getName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }
}
