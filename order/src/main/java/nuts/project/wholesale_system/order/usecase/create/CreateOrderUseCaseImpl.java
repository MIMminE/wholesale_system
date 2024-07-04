package nuts.project.wholesale_system.order.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static nuts.project.wholesale_system.order.domain.dto.ResponseType.*;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse execute(String userId, List<OrderItem> items) {

        OrderEntity orderEntity = orderRepository.save(OrderEntity.fromOrder(createOrder(userId, items)));

        OrderResponse orderResponse = OrderResponse.builder()
                .userId(orderEntity.getUserId())
                .processAt(orderEntity.getCreatedAt())
                .responseType(CREATE)
                .build();
        orderResponse.addOrderInformation("orderId", orderEntity.getOrderId());


        return orderResponse;
    }

    private Order createOrder(String userId, List<OrderItem> items) {
        return new Order(UUID.randomUUID().toString(), userId, items, OrderStatus.pendPayment);
    }

}
