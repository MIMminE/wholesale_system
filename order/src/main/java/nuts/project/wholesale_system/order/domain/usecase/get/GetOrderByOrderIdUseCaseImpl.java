package nuts.project.wholesale_system.order.domain.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.dto.ResponseType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetOrderByOrderIdUseCaseImpl implements GetOrderByOrderIdUseCase {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse execute(String userId, String orderId) {

        OrderEntity orderEntity = orderRepository.findByUserIdAndOrderId(userId, orderId).orElseThrow();

        OrderResponse orderResponse = OrderResponse.builder()
                .userId(orderEntity.getUserId())
                .processAt(LocalDateTime.now())
                .responseType(ResponseType.GET)
                .build();

        List<Map<String, Object>> items = new ArrayList<>();

        for (OrderItemEntity item : orderEntity.getItems())
            items.add(Map.of("productId", item.getProductId(), "quantity", item.getQuantity()));
        orderResponse.addOrderInformation("items", items);
        return orderResponse;
    }
}
