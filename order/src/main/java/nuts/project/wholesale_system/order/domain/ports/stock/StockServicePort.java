package nuts.project.wholesale_system.order.domain.ports.stock;

import nuts.project.wholesale_system.order.domain.exception.StockException;

import java.util.Optional;

public interface StockServicePort {

    Optional<StockResponse> deductStock(StockRequest request) throws StockException;

    Optional<StockResponse> returnStock(StockRequest request) throws StockException;

    Optional<StockResponse> updateStock(StockRequest request) throws StockException;
}
