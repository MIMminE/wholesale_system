package nuts.project.wholesale_system.order.domain.service.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final PaymentServicePort paymentService;

    @Override
    public OrderProcessDto execute(String userId, List<OrderItem> items) {
        try {
            PaymentInformation payResponse = getPaymentInformation();
            OrderEntity orderEntity = orderRepository.save(createOrderEntity(userId, items));

            Order resultOrder = orderEntity.toOrder();

            return new OrderProcessDto(payResponse, resultOrder);
        } catch (RuntimeException e) {
            throw new RuntimeException("payment service is failed");
        }
    }

    private PaymentInformation getPaymentInformation() {
        PaymentResponse paymentInformation = paymentService.requestPayment(new PaymentRequest());
        return new PaymentInformation(paymentInformation.getAccountId());
    }

    private OrderEntity createOrderEntity(String userId, List<OrderItem> items) {

        OrderEntity orderEntity = new OrderEntity(UUID.randomUUID().toString(), userId, OrderStatus.pendPayment);

        items.forEach(e ->
                orderEntity.addItem(new OrderItemEntity(e.getProductId(), e.getQuantity())));

        return orderEntity;
    }
}
