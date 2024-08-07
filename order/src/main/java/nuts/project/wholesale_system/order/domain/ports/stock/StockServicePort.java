package nuts.project.wholesale_system.order.domain.ports.stock;

import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;

public interface StockServicePort {

    StockResponse deductStock(StockDeductRequest request);

    StockResponse returnStock(StockReturnRequest request);
}
