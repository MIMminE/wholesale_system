package nuts.project.wholesale_system.order.domain.ports.stock.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class StockDeductRequest {
    List<RequestItem> items;
}


