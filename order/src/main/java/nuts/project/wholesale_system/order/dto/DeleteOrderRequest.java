package nuts.project.wholesale_system.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeleteOrderRequest {

    @NotBlank
    String userId;

    @NotBlank
    String orderId;
}
