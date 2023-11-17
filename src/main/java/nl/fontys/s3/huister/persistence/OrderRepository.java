package nl.fontys.s3.huister.persistence;

import nl.fontys.s3.huister.persistence.entities.OrderEntity;
import nl.fontys.s3.huister.persistence.entities.UserEntity;
import nl.fontys.s3.huister.persistence.entities.enumerator.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Modifying
    @Query("UPDATE OrderEntity SET status=:status WHERE id=:id")
    void updateOrder(@Param("id")long id, @Param("status")OrderStatus status);

    List<OrderEntity> findAllByOwnerOrCustomer(UserEntity owner,UserEntity customer);
    int countByOwnerAndStatus(UserEntity owner,OrderStatus status);
    boolean existsById(long id);
}
