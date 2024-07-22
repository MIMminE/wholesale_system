package nuts.project.wholesale_system.log.repository.order;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, String> {
    OrderLog findByLog(String log);
}
