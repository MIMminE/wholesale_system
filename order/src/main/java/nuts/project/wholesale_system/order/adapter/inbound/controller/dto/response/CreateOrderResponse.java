package nuts.project.wholesale_system.order.adapter.inbound.controller.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CreateOrderResponse {

    @NotBlank
    private String orderId;

    @NotBlank
    private String userId;

    @NotEmpty
    private PaymentInformation paymentInformation;

    @NotEmpty
    private List<OrderItemResponse> orderItems;


    public static CreateOrderResponse fromOrder(OrderProcessDto createOrderDto) {

        return new CreateOrderResponse(createOrderDto.getOrder().getOrderId(), createOrderDto.getOrder().getUserId(),
                createOrderDto.getPaymentInformation(),
                createOrderDto.getOrder().getItems()
                        .stream().map(OrderItemResponse::fromOrderItem).toList());
    }

}
