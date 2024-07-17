package nuts.project.wholesale_system.order.domain.ports.stock;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockRequest {
    private List<OrderItem> requestItems;
}
