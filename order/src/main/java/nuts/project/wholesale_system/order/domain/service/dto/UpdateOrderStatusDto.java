package nuts.project.wholesale_system.order.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateOrderStatusDto {

    private String orderId;
    private String beforeOrderStatus;
    private String afterOrderStatus;

}
