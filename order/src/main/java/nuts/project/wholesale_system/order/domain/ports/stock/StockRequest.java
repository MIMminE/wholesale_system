package nuts.project.wholesale_system.order.domain.ports.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

@AllArgsConstructor
@Getter
public class StockRequest {
    private StockRequestType requestType;
    private List<OrderItem> requestItems;
}
