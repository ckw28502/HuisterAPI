package nl.fontys.s3.huister.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "ORDER_TABLE")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @NotNull
    @Column(name = "duration")
    private int duration;

    @NotNull
    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Setter
    private OrderStatus status;
}
