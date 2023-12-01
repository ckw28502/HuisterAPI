package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.persistence.entities.PropertyEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<PropertyEntity,Long> {
    List<PropertyEntity> findAllByEndRentIsNull();

    @Procedure("save_property")
    void saveProperty(
            @Param("owner_id")long ownerId,
            @Param("city_name")String cityName,
            @Param("street_name")String streetName,
            @Param("post_code")String postCode,
            @Param("description")String description,
            @Param("image_url")String imageUrl,
            @Param("area")double area,
            @Param("price")double price,
            @Param("house_number")int houseNumber
    );

    @Procedure("delete_property")
    void deleteProperty(@Param("property_id")long propertyId);

    Optional<PropertyEntity> findByIdAndIsDeletedIsNull(long id);

    @NonNull
    List<PropertyEntity> findAllByIsDeletedIsNull();
    List<PropertyEntity> findAllByOwnerIdAndIsDeletedIsNull(long ownerId);
    @Modifying
    @Query("UPDATE PropertyEntity SET imageUrl=:imageUrl,description=:description,price=:price WHERE id=:id")
    void updateProperty(@Param("id")long id,@Param("imageUrl")String imageUrl,@Param("description")String description, @Param("price")double price);
    int countByOwnerAndIsDeletedIsNull(UserEntity owner);

    int countByEndRentIsNotNullAndIsDeletedIsNull();
    int countByEndRentIsNullAndIsDeletedIsNull();

    int countByEndRentIsNotNullAndOwnerAndIsDeletedIsNull(UserEntity owner);
    int countByEndRentIsNullAndOwnerAndIsDeletedIsNull(UserEntity owner);


}
