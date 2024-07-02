package nuts.project.wholesale_system.order.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    private String productId;
    private int quantity;

    static public OrderItem createOrderItem(String productId, int quantity) {
        return new OrderItem(productId, quantity);
    }
}
