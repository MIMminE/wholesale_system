package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateOrderResponse {

    @NotBlank
    private String orderId;

    @NotBlank
    private String userId;

    @NotEmpty
    private List<OrderItemResponse> beforeOrderItems;

    @NotEmpty
    private List<OrderItemResponse> afterOrderItems;

    public static UpdateOrderResponse fromUpdateOrderDto(UpdateOrderDto updateOrderDto) {

        String orderId = updateOrderDto.getOrderId();
        String userId = updateOrderDto.getUserId();

        List<OrderItemResponse> beforeOrderItems = updateOrderDto.getBeforeOrderItems().stream()
                .map(OrderItemResponse::fromOrderItem).toList();
        List<OrderItemResponse> afterOrderItems = updateOrderDto.getAfterOrderItems().stream()
                .map(OrderItemResponse::fromOrderItem).toList();

        return new UpdateOrderResponse(orderId, userId, beforeOrderItems, afterOrderItems);
    }
}
