package nuts.project.wholesale_system.order.domain.service.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentDeleteRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentDeleteResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.RequestItem;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.*;

@Component
@RequiredArgsConstructor
public class DeleteOrderByOrderIdUseCaseImpl implements DeleteOrderIdUseCase {
    private final OrderRepository orderRepository;
    private final StockServicePort stockService;
    private final PaymentServicePort paymentService;

    @Override
    public Order execute(String orderId) throws OrderException {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(DELETE_NO_SUCH_ELEMENT));
        orderRepository.delete(orderEntity);

        List<RequestItem> requestItems = orderEntity.getItems().stream().map(
                e -> new RequestItem(e.getProductId(), e.getQuantity())).toList();

        stockService.returnStock(new StockReturnRequest(requestItems));
        PaymentDeleteResponse paymentDeleteResponse = paymentService.deletePaymentInformation(new PaymentDeleteRequest(orderId));
        System.out.println(paymentDeleteResponse);
        if (paymentDeleteResponse.isDeleted())
            return orderEntity.toOrder();
        else
            throw new OrderException(PAYMENT_NOT_FOUND);
    }
}
