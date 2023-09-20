package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UsernameExistException;
import nl.fontys.s3.huister.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.huister.domain.request.user.CreateUserRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    @Override
    public void createUser(CreateUserRequest request) {
        if (userRepository.usernameExist(request.getUsername())){
            throw new UsernameExistException();
        }
        userRepository.createUser(request);
    }
}
