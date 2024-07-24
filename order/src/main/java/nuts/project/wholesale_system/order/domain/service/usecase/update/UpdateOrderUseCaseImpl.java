package nuts.project.wholesale_system.order.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.*;

@Component
@RequiredArgsConstructor
public class UpdateOrderUseCaseImpl implements UpdateOrderUseCase {

    private final OrderRepository orderRepository;
    private final StockServicePort stockServicePort;

    @Override
    public UpdateOrderDto execute(String orderId, List<OrderItem> items) throws StockException {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(UPDATE_NO_SUCH_ELEMENT));
        Order beforeOrder = orderEntity.toOrder();

        StockResponse stockResponse = stockServicePort.updateStock(new StockRequest(items)).orElseThrow();

        items.stream().map(e -> new OrderItemEntity(e.getProductId(), e.getQuantity()))
                .forEach(orderEntity::addItem);

        Order afterOrder = orderEntity.toOrder();

        return new UpdateOrderDto(orderId, orderEntity.getUserId(), beforeOrder.getItems(), afterOrder.getItems());
    }
}
