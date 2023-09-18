package nl.fontys.s3.huister.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private int role; //0 = admin, 1 = Owner, 2 = Customer
    private String username,password,name,phoneNumber,cardNumber,profilePictureUrl;
}
