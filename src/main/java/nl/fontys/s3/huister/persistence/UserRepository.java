package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findById(final long id);
    Optional<UserEntity> findByUsername(final String username);

    boolean existsByUsername(String username);

    Optional<UserEntity>findByToken(String token);


    @Modifying
    @Query("UPDATE UserEntity SET profilePictureUrl=:profilePictureUrl WHERE username=:username")
    void setProfilePictureUrl(@Param("username") String username,@Param("profilePictureUrl") String newPassword);

    @Modifying
    @Query("UPDATE UserEntity SET password=:newPassword WHERE id=:id")
    void setPassword(@Param("id") long id,@Param("newPassword") String newPassword);
    @Modifying
    @Query("UPDATE UserEntity SET name=:name,phoneNumber=:phoneNumber WHERE id=:id")
    void updateUser(@Param("name") String name,@Param("phoneNumber")String phoneNumber,@Param("id")long id);
    @Modifying
    @Query("UPDATE UserEntity SET activated=true WHERE username=:username")
    void activateAccount(@Param("username") String username);
    List<UserEntity> findAllByRole(UserRole role);

    @Modifying
    @Query("UPDATE UserEntity SET token=:token WHERE username=:username")
    void saveToken(@Param("token")String token, @Param("username") String username);
}
