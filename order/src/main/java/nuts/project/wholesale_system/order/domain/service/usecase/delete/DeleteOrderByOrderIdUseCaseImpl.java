package nuts.project.wholesale_system.order.domain.service.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.request.PaymentDeleteRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentDeleteResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import org.springframework.stereotype.Component;

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

        orderEntity.getItems();

        stockService.returnStock(new StockReturnRequest());
        PaymentDeleteResponse paymentDeleteResponse = paymentService.deletePaymentInformation(new PaymentDeleteRequest(orderId));

        return orderEntity.toOrder();
    }
}
