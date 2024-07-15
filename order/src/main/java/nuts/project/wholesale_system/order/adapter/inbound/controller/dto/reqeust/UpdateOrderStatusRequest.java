package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class UpdateOrderStatusRequest {

    @NotBlank
    String orderId;

    @NotBlank
    String orderStatus;
}
