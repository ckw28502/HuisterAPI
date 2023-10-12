package nl.fontys.s3.huister.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.huister.business.request.order.CreateOrderRequest;
import nl.fontys.s3.huister.business.request.order.UpdateOrderRequest;
import nl.fontys.s3.huister.business.request.property.CreatePropertyRequest;
import nl.fontys.s3.huister.business.request.user.CreateUserRequest;
import nl.fontys.s3.huister.domain.entities.OrderEntity;
import nl.fontys.s3.huister.domain.entities.enumerator.OrderStatus;
import nl.fontys.s3.huister.domain.entities.enumerator.UserRole;
import nl.fontys.s3.huister.persistence.CityRepository;
import nl.fontys.s3.huister.persistence.OrderRepository;
import nl.fontys.s3.huister.persistence.PropertyRepository;
import nl.fontys.s3.huister.persistence.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer {
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private PropertyRepository propertyRepository;
    private OrderRepository orderRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData(){
        //users

        //admin
        userRepository.createUser(CreateUserRequest.builder()
                .username("admin")
                .role(UserRole.ADMIN)
                .password("admin")
                .name("admin")
                .email("admin@email.com")
                .phoneNumber("0123456789")
                .build());

        //owner
        userRepository.createUser(CreateUserRequest.builder()
                .username("malexander2")
                .role(UserRole.OWNER)
                .password("xL8,cJ#9")
                .name("Maddi Alexander")
                .email("malexander2@biglobe.ne.jp")
                .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fmalexander2.jpg?alt=media&amp;token=c1661cd0-7f06-4f50-9b2f-f45616946a89")
                .phoneNumber("973-713-7972")
                .build());
        userRepository.createUser(CreateUserRequest.builder()
                .username("vpluck0")
                .role(UserRole.OWNER)
                .password("hJ6\\1?PtUE|76v")
                .name("Vaughan Pluck")
                .email("vpluck0@nhs.uk")
                .phoneNumber("209-703-3693")
                .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fvpluck0.jpg?alt=media&amp;token=675d5cae-2878-442e-91f8-c92aca17b374")
                .build());
        userRepository.createUser(CreateUserRequest.builder()
                .username("wwiffill1")
                .role(UserRole.CUSTOMER)
                .profilePictureUrl("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fuser%2Fwwiffill1.jpg?alt=media&amp;token=3a7aac4c-c091-42be-b792-c1711a92786c")
                .password("fX7(mDdmj`43I}}")
                .name("Willem Wiffill")
                .email("wwiffill1@nhs.uk")
                .phoneNumber("435-793-1696")
                .build());
        userRepository.activateAccount(1);
        userRepository.activateAccount(2);
        userRepository.activateAccount(4);


        //city

        cityRepository.createCity("Amsterdam");
        cityRepository.createCity("Eindhoven");

        //Property

        propertyRepository.createProperty(CreatePropertyRequest.builder()
                        .cityId(1)
                        .area(11.75)
                        .price(400)
                        .description("Good Place")
                        .ownerId(2)
                        .postCode("1008DG")
                        .cityName("Amsterdam")
                        .streetName("Hoort bij postbussen")
                        .imageUrls(List.of("https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fproperty%2F1%2FFront%20View.jpg?alt=media&token=7491c02e-eecc-4973-b04b-662f605cec84&_gl=1*30i7pw*_ga*Mzc3NjQ5ODU0LjE2OTcwMTExMDA.*_ga_CW55HF8NVT*MTY5NzA5OTM3OC41LjEuMTY5NzA5OTY0OS4xMi4wLjA."
                        ,"https://firebasestorage.googleapis.com/v0/b/huister-83675.appspot.com/o/images%2Fproperty%2F1%2FLiving%20Room.jpg?alt=media&token=04da8323-8d61-4b4f-a29b-5e2b84523c87&_gl=1*3he3kd*_ga*Mzc3NjQ5ODU0LjE2OTcwMTExMDA.*_ga_CW55HF8NVT*MTY5NzA5OTM3OC41LjEuMTY5NzEwMDU0NC40NS4wLjA."))
                .build());

        //Order

        orderRepository.createOrder(CreateOrderRequest.builder()
                .customerId(4)
                .price(395.65)
                .propertyId(1)
                .ownerId(2)
                .duration(5)
                .build());

        OrderEntity order=orderRepository.updateOrder(UpdateOrderRequest.builder()
                .id(1)
                .status(OrderStatus.ACCEPTED)
                .build());

        propertyRepository.rentProperty(order);

    }
}
