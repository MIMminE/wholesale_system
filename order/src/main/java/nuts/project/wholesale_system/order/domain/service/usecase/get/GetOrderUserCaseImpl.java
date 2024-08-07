package nuts.project.wholesale_system.order.domain.service.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.ports.payment.response.PaymentResponse;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrderUserCaseImpl implements GetOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentServicePort paymentService;

    @Override
    public OrderProcessDto execute(String orderId) {
        PaymentResponse paymentResponse = paymentService.getPayment(orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT));

        String userId = paymentResponse.getUserId();
        String accountNumber = paymentResponse.getAccountNumber();

        return new OrderProcessDto(new PaymentInformation(userId, orderId, accountNumber), orderEntity.toOrder());
    }
}
