package nuts.project.wholesale_system.order.domain.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static nuts.project.wholesale_system.order.domain.dto.ResponseType.*;

@Component
@RequiredArgsConstructor
public class DeleteOrderByOrderIdUseCaseImpl implements DeleteOrderByOrderIdUseCase {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse execute(String orderId) {
        orderRepository.deleteById(orderId);

        OrderResponse orderResponse = OrderResponse.builder()
                .processAt(LocalDateTime.now())
                .responseType(DELETE)
                .build();

        orderResponse.addOrderInformation("orderId", orderId);

        return orderResponse;
    }
}
