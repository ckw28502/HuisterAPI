package nl.fontys.s3.huister.business.request.property;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdatePropertyRequest {
    @Setter
    private long id;
    private String description;
    private String imageUrl;
    private double price;
}
