package nl.fontys.s3.huister.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseInitializer {
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData(){
        //users

        //admin
        userRepository.createUser(CreateUserRequest.builder()
                .username("admin")
                .role(UserRole.ADMIN)
                //.profilePictureUrl("Image.png")
                .password("admin")
                .name("admin")
                .email("admin@email.com")
                .phoneNumber("0123456789")
                .build());

        //owner
        userRepository.createUser(CreateUserRequest.builder()
                .username("malexander2")
                .role(UserRole.OWNER)
                //.profilePictureUrl("Image.png")
                .password("xL8,cJ#9")
                .name("Maddi Alexander")
                .email("malexander2@biglobe.ne.jp")
                .phoneNumber("973-713-7972")
                .build());
        userRepository.createUser(CreateUserRequest.builder()
                .username("vpluck0")
                .role(UserRole.OWNER)
                //.profilePictureUrl("Image.png")
                .password("hJ6\\1?PtUE|76v")
                .name("Vaughan Pluck")
                .email("vpluck0@nhs.uk")
                .phoneNumber("209-703-3693")
                .build());
        userRepository.createUser(CreateUserRequest.builder()
                .username("wwiffill1")
                .role(UserRole.OWNER)
                //.profilePictureUrl("Image.png")
                .password("fX7(mDdmj`43I}}")
                .name("Willem Wiffill")
                .email("wwiffill1@nhs.uk")
                .phoneNumber("435-793-1696")
                .build());
        userRepository.activateAccount(1);
    }
}
