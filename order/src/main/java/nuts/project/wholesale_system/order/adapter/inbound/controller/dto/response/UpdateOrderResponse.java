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

    @NotEmpty
    private PaymentInformation paymentInformation;

    public static UpdateOrderResponse fromUpdateOrderDto(UpdateOrderDto updateOrderDto) {

        String orderId = updateOrderDto.getOrderId();
        String userId = updateOrderDto.getUserId();

        List<OrderItemResponse> beforeOrderItems = updateOrderDto.getBeforeOrder().getItems().stream()
                .map(OrderItemResponse::fromOrderItem).toList();
        List<OrderItemResponse> afterOrderItems = updateOrderDto.getAfterOrder().getItems().stream()
                .map(OrderItemResponse::fromOrderItem).toList();

        PaymentInformation paymentInformation = updateOrderDto.getPaymentInformation();

        return new UpdateOrderResponse(orderId, userId, beforeOrderItems, afterOrderItems, paymentInformation);
    }
}
