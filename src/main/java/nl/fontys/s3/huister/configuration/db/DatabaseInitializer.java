package nl.fontys.s3.huister.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.domain.entities.CityEntity;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.PropertyEntity;
import nl.fontys.s3.huister.domain.entities.UserEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer {
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private PropertyRepository propertyRepository;
    private OrderRepository orderRepository;
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData(){
        if (userRepository.findById(1L).isEmpty()){
            //users

            //admin
            userRepository.save(UserEntity.builder()
                    .username("admin")
                    .role(UserRole.ADMIN)
                    .password(passwordEncoder.encode("admin"))
                    .name("admin")
                    .email("admin@email.com")
                    .phoneNumber("0123456789")
                    .activated(true)
                    .build());

            //owner
            UserEntity owner=UserEntity.builder()
                    .activated(true)
                    .username("malexander2")
                    .role(UserRole.OWNER)
                    .password(passwordEncoder.encode("malexander2"))
                    .name("Maddi Alexander")
                    .email("malexander2@biglobe.ne.jp")
                    .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fmalexander2.jpg?alt=media&amp;token=c1661cd0-7f06-4f50-9b2f-f45616946a89")
                    .phoneNumber("973-713-7972")
                    .build();
            userRepository.save(owner);
            userRepository.save(UserEntity.builder()
                    .activated(false)
                    .username("vpluck0")
                    .role(UserRole.OWNER)
                    .password(passwordEncoder.encode("vpluck0"))
                    .name("Vaughan Pluck")
                    .email("vpluck0@nhs.uk")
                    .phoneNumber("209-703-3693")
                    .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fvpluck0.jpg?alt=media&amp;token=675d5cae-2878-442e-91f8-c92aca17b374")
                    .build());
            UserEntity customer=UserEntity.builder()
                    .activated(true)
                    .username("wwiffill1")
                    .role(UserRole.CUSTOMER)
                    .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fwwiffill1.jpg?alt=media&amp;token=3a7aac4c-c091-42be-b792-c1711a92786c")
                    .password("wwiffill1")
                    .name("Willem Wiffill")
                    .email("wwiffill1@nhs.uk")
                    .phoneNumber("435-793-1696")
                    .build();
            userRepository.save(customer);


            //city

            CityEntity city1=CityEntity.builder().name("Amsterdam").build();
            CityEntity city2=CityEntity.builder().name("Eindhoven").build();
            cityRepository.saveAll(List.of(city1,city2));

            //Property

            PropertyEntity property1=PropertyEntity.builder()
                    .city(city1)
                    .area(11.75)
                    .price(400)
                    .description("Good Place")
                    .owner(owner)
                    .postCode("1008DG")
                    .streetName("Hoort bij postbussen")
                    .imageUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fproperty%2F1.jpg?alt=media&token=14309b10-5367-487a-a6c8-4e311c1c01a6&_gl=1*72g6xg*_ga*Mzc3NjQ5ODU0LjE2OTcwMTExMDA.*_ga_CW55HF8NVT*MTY5ODgzOTE5Mi4xMS4xLjE2OTg4Mzk0MDEuNjAuMC4w")
                    .endRent(LocalDate.now().plusMonths(5))
                    .build();
            propertyRepository.save(property1);

            propertyRepository.save(PropertyEntity.builder()
                    .city(city2)
                    .area(15.25)
                    .price(594.75)
                    .description("Good Place")
                    .owner(owner)
                    .postCode("5612JN")
                    .streetName("'S Gravesandestraat")
                    .imageUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fproperty%2F2.jpg?alt=media&token=86c7951a-18a1-46db-a154-f57a4ec26a28&_gl=1*1gonx7n*_ga*Mzc3NjQ5ODU0LjE2OTcwMTExMDA.*_ga_CW55HF8NVT*MTY5ODgzOTE5Mi4xMS4xLjE2OTg4Mzk0NDYuMTUuMC4w")
                    .build());

            //Order

            orderRepository.save(OrderEntity.builder()
                    .customer(customer)
                    .price(395.65)
                    .property(property1)
                    .owner(owner)
                    .duration(5)
                    .status(OrderStatus.ACCEPTED)
                    .build());

        }
    }
}
