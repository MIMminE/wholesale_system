package nuts.project.wholesale_system.order.adapter.outbound.stock;

import nuts.project.wholesale_system.order.domain.exception.StockException;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static nuts.project.wholesale_system.order.domain.exception.StockException.StockExceptionCase.*;

@Component
public class StockService implements StockServicePort {

    @Override
    public Optional<StockResponse> checkStock(StockRequest request) throws StockException {
        try {
            System.out.println("stock check!");

            if (request.getRequestItems() == null) {
                throw new StockException(OUT_OF_STOCK);
            }

            return Optional.empty();
        } catch (RuntimeException e) {
            throw new StockException(STOCK_SERVICE_FAIL);
        }
    }

    @Override
    public Optional<StockResponse> reserveStock(StockRequest request) {
        return Optional.empty();
    }
}
