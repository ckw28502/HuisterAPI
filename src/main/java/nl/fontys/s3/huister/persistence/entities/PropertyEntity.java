package nl.fontys.s3.huister.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PROPERTY_TABLE")
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @Column(name = "street_name")
    @NotBlank
    private String streetName;

    @Column(name = "house_number")
    @NotNull
    private int houseNumber;

    @Column(name = "post_code")
    @NotBlank
    private String postCode;
    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "image_url",columnDefinition = "TEXT")
    @NotBlank
    private String imageUrl;

    @Column(name = "area")
    @NotNull
    private double area;
    @Column(name = "price")
    @NotNull
    private double price;

    @Column(name = "end_rent")
    private LocalDate endRent;
}
