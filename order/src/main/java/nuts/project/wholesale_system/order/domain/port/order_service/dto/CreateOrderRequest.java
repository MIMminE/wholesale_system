package nuts.project.wholesale_system.order.domain.port.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateOrderRequest {

    @NotBlank
    private String userId;

    @NotEmpty
    private List<OrderItemRequest> orderItems;


}
