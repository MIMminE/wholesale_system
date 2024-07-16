package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusResponse {

    private String orderId;
    private String beforeStatus;
    private String afterStatus;

    public static UpdateOrderStatusResponse fromUpdateOrderStatusDto(UpdateOrderStatusDto updateOrderStatusDto) {
        return new UpdateOrderStatusResponse(updateOrderStatusDto.getOrderId(), updateOrderStatusDto.getBeforeOrderStatus(), updateOrderStatusDto.getAfterOrderStatus());
    }

}