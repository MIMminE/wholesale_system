package nuts.project.wholesale_system.order.domain.ports.stock;

import java.util.Optional;

public interface StockServicePort {

    Optional<StockResponse> deductStock(StockRequest request);

    Optional<StockResponse> returnStock(StockRequest request);

    Optional<StockResponse> updateStock(StockRequest request);
}
