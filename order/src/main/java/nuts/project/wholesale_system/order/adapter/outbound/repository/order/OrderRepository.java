package nuts.project.wholesale_system.order.adapter.outbound.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    Optional<List<OrderEntity>> getOrderEntitiesByUserId(String userId);

    Optional<OrderEntity> findByUserId(String userId, String orderId);

}
