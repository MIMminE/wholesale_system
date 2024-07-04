package nuts.project.wholesale_system.order.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.dto.ResponseType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrdersByUserIdUseCaseImpl implements GetOrdersByUserIdUseCase {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse execute(String userId) {

        List<OrderEntity> entities = orderRepository.getOrderEntitiesByUserId(userId);

        OrderResponse orderResponse = OrderResponse.builder()
                .userId(userId)
                .processAt(LocalDateTime.now())
                .responseType(ResponseType.GET)
                .build();

        List<String> orderIds = entities.stream().map(OrderEntity::getOrderId).toList();
        orderResponse.addOrderInformation("orderIds", orderIds);
        return orderResponse;
    }
}
