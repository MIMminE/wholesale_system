package nuts.project.wholesale_system.order.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItem {
    private String productId;
    private int quantity;
}
