package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class CreateOrderRequest {

    @NotEmpty
    private List<OrderItemRequest> orderItems;

}