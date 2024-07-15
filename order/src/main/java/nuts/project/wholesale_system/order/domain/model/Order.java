package nuts.project.wholesale_system.order.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private OrderStatus orderStatus;
}
