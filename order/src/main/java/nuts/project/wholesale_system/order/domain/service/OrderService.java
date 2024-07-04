package nuts.project.wholesale_system.order.domain.service;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.dto.OrderResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentRequest;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.usecase.create.CreateOrderUseCase;
import nuts.project.wholesale_system.order.usecase.delete.DeleteOrderByOrderIdUseCase;
import nuts.project.wholesale_system.order.usecase.get.GetOrderByOrderIdUseCase;
import nuts.project.wholesale_system.order.usecase.get.GetOrdersByUserIdUseCase;
import nuts.project.wholesale_system.order.domain.dto.CreateOrderRequest;
import nuts.project.wholesale_system.order.domain.dto.CreateOrderRequest.OrderItemRequest;
import nuts.project.wholesale_system.order.domain.dto.DeleteOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CreateOrderUseCase createOrderUseCase;
    private final DeleteOrderByOrderIdUseCase deleteOrderByOrderIdUseCase;
    private final GetOrdersByUserIdUseCase getOrdersByUserIdUseCase;
    private final GetOrderByOrderIdUseCase getOrderByOrderIdUseCase;

    private final PaymentServicePort paymentService;

    public OrderResponse createOrder(CreateOrderRequest request) {

        OrderResponse orderResponse = createOrderUseCase.execute(request.getUserId(),
                request.getOrderItems().stream().map(OrderItemRequest::toOrderItem).toList());
        PaymentResponse paymentResponse = paymentService.getPaymentInformation(new PaymentRequest());

        orderResponse.addOrderInformation("payment_info", paymentResponse);
        return orderResponse;
    }

    public OrderResponse deleteOrder(DeleteOrderRequest request) {

        OrderResponse orderResponse = deleteOrderByOrderIdUseCase.execute(request.getOrderId());
        orderResponse.setUserId(request.getUserId());
        return orderResponse;
    }

    public OrderResponse getOrdersByUserId(String userId) {
        return getOrdersByUserIdUseCase.execute(userId);
    }

    public OrderResponse getOrderByOrderId(String userId, String orderId) {
        return getOrderByOrderIdUseCase.execute(userId, orderId);
    }
}
