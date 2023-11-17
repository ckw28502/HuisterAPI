package nl.fontys.s3.huister.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "USER_TABLE")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotBlank
    private String password;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "email")
    @NotBlank
    private String email;
    @Column(name = "phone_number")
    @NotBlank
    private String phoneNumber;
    @Column(name = "profile_picture_url",columnDefinition = "TEXT")
    private String profilePictureUrl;
    @Column(name = "activated")
    private boolean activated;
}
