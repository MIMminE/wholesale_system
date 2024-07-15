package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class DeleteOrderRequest {

    @NotBlank
    String orderId;
}
