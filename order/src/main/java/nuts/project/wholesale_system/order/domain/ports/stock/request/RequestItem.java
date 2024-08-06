package nuts.project.wholesale_system.order.domain.ports.stock.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

@AllArgsConstructor
@Getter
@ToString
public class RequestItem {
    private String productId;
    private int quantity;

    public OrderItem toOrderItem() {
        return new OrderItem(productId, quantity);
    }
}
