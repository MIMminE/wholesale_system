package nuts.project.wholesale_system.order.domain.service.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderRepository;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentServicePort;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.domain.service.dto.PaymentInformation;
import org.springframework.stereotype.Component;

import java.util.List;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.*;

@Component
@RequiredArgsConstructor
public class GetOrdersUseCaseImpl implements GetOrdersUseCase {
    private final OrderRepository orderRepository;
    private final PaymentServicePort paymentService;

    @Override
    public List<OrderProcessDto> execute(String userId) {

        List<OrderEntity> orderEntities = orderRepository.getOrderEntitiesByUserId(userId)
                .orElseThrow(() -> new OrderException(GET_NO_SUCH_ELEMENT));
        if (orderEntities.isEmpty())
            throw new OrderException(GET_NO_SUCH_ELEMENT);

        return orderEntities.stream().map(e -> {
            PaymentResponse paymentInformation = paymentService.getPaymentInformation(e.getOrderId());
            return new OrderProcessDto(new PaymentInformation(paymentInformation.getAccountId()), e.toOrder());
        }).toList();
    }
}
