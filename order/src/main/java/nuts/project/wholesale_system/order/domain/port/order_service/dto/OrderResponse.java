package nuts.project.wholesale_system.order.domain.port.order_service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nuts.project.wholesale_system.order.domain.port.payment_service.dto.PaymentResponse;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrderResponse {
    private String userId;
    private String orderId;
    private LocalDateTime createdAt;
    private Map<String, Object> orderInformation;

    public static OrderResponse generateCreateOrderResponse(String userId, String orderId, LocalDateTime createdAt, PaymentResponse paymentResponse) {
        return OrderResponse.builder()
                .userId(userId)
                .orderId(orderId)
                .createdAt(createdAt)
                .orderInformation(Map.of("paymentResponse", paymentResponse))
                .build();
    }


}
