package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import nuts.project.wholesale_system.order.domain.model.OrderItem;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@ToString
public class OrderItemRequest {

    @NotBlank
    private String productId;

    @Min(1)
    private int quantity;

    public OrderItem toOrderItem() {
        return new OrderItem(this.productId, this.quantity);
    }
}
