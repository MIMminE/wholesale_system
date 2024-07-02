package nuts.project.wholesale_system.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private OrderStatus orderStatus;
}
