package nuts.project.wholesale_system.order.domain.ports.stock.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
public class StockDeductRequest {
    List<RequestItem> items;
}


