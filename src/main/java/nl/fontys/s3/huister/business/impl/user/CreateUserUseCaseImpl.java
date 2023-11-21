package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UsernameExistException;
import nl.fontys.s3.huister.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private JavaMailSender javaMailSender;

    /**
     *
     * @param request contains new user data
     *
     * @should throw UsernameExistException when username exists
     * @should create user when username is unique
     */
    @Override
    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistException();
        }
        UserEntity newUser=UserEntity.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .profilePictureUrl(request.getProfilePictureUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .email(request.getEmail())
                .activated(false)
                .build();
        userRepository.save(newUser);

        String encodedUsername= URLEncoder.encode(request.getUsername(), StandardCharsets.UTF_8);
        //Email
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("ACTIVATE YOUR HUISTER ACCOUNT!!!");
        message.setText("Click this link to activate your account!\n" +
                "http://localhost:5173/activate/"+encodedUsername);
        javaMailSender.send(message);
    }
}
