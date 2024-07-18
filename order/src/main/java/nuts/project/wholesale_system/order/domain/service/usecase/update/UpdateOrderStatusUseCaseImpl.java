package nuts.project.wholesale_system.order.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;
    private final StockServicePort stockService;

    @Override
    public UpdateOrderStatusDto execute(String orderId, OrderStatus orderStatus) {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(UPDATE_NO_SUCH_ELEMENT));
        OrderStatus beforeOrderStatus = orderEntity.getOrderStatus();
        orderEntity.setOrderStatus(orderStatus);

        List<OrderItem> orderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();

        if (orderStatus.equals(OrderStatus.cancelled)) {
            StockResponse stockResponse = stockService.returnStock(new StockRequest(StockRequestType.Return, orderItems)).orElseThrow();
        }

        return new UpdateOrderStatusDto(orderId, beforeOrderStatus.toString(), orderStatus.toString());
    }
}
