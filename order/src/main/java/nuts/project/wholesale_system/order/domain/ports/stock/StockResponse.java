package nuts.project.wholesale_system.order.domain.ports.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockResponse {
    private StockRequestType responseType;
    private boolean result;
}
