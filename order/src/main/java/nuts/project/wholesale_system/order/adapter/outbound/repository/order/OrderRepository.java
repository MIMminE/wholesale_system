package nuts.project.wholesale_system.order.adapter.outbound.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    List<OrderEntity> getOrderEntitiesByUserId(String userId);

}
