package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class UpdateOrderRequest {

    @NotBlank
    private String orderId;

    @NotEmpty
    private List<OrderItemRequest> orderItems;
}
