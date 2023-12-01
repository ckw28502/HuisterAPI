package nl.fontys.s3.huister.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.exception.user.UserNotFoundException;
import nl.fontys.s3.huister.business.interfaces.user.ForgotPasswordUseCase;
import nl.fontys.s3.huister.business.request.user.ForgotPasswordRequest;
import nl.fontys.s3.huister.persistence.UserRepository;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ForgotPasswordUseCaseImpl implements ForgotPasswordUseCase {
    private UserRepository userRepository;
    private JavaMailSender sender;

    /**
     *
     * @param request Forgot Password Request
     *
     * @should throw UserNotFoundException when user is not found
     * @should send an email if user is found
     */
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        Optional<UserEntity>optionalUser=userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }

        UserEntity user=optionalUser.get();

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("CREATE NEW PASSWORD");
        message.setText("Click this link below to create new password\n" +
                "http://localhost:5173/change/"+user.getUsername());

        sender.send(message);
    }
}
