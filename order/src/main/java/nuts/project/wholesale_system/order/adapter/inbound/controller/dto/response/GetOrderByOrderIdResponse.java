package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetOrderByOrderIdResponse {

    @NotBlank
    private String orderId;

    @NotBlank
    private String userId;

    @NotEmpty
    private PaymentInformation payment;

    @NotEmpty
    private List<OrderItemResponse> orderItems;

    public static GetOrderByOrderIdResponse fromOrderProcessDto(OrderProcessDto createOrderDto) {

        return new GetOrderByOrderIdResponse(createOrderDto.getOrder().getOrderId(), createOrderDto.getOrder().getUserId(),
                createOrderDto.getPayment(),
                createOrderDto.getOrder().getItems()
                        .stream().map(OrderItemResponse::fromOrderItem).toList());
    }
}
