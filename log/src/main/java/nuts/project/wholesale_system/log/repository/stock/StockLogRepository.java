package nuts.project.wholesale_system.log.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLogRepository extends JpaRepository<StockLog, String> {
    StockLog findByLog(String log);
}
