package nuts.project.wholesale_system.order.domain.service.usecase.create;

import nuts.project.wholesale_system.order.domain.exception.PaymentException;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.UUID;

import static nuts.project.wholesale_system.order.domain.exception.PaymentException.PaymentExceptionCase.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class CreateOrderUseCaseTest extends UseCaseTestSupport {

    @DisplayName("createOrderUseCase 성공 테스트")
    @Commit
    @Test
    void createOrderUseCaseSuccess() {

        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();
        String accountId = UUID.randomUUID().toString();
        PaymentResponse paymentResponse = new PaymentResponse(accountId, "test", 10);
        BDDMockito.given(paymentService.requestPayment(eq(userId), anyString())).willReturn(paymentResponse);

        // when
        OrderProcessDto result = createOrderUseCase.execute(userId, items);

        // then
        Assertions.assertThat(result)
                .extracting("paymentInformation.payInfo", "order.userId", "order.orderStatus")
                .contains(accountId, userId, OrderStatus.pendPayment);
    }

    @DisplayName("createOrderUseCase 예외 발생 테스트 : 결제 시스템과의 통신에 실패한 경우")
    @Test
    void createOrderUseCaseException() {

        // given
        Order order = getOrderedObject(Order.class).get(0);
        String userId = order.getUserId();
        List<OrderItem> items = order.getItems();

        BDDMockito.given(paymentService.requestPayment(eq(userId), anyString()))
                .willThrow(new PaymentException(PAYMENT_SERVICE_FAIL));

        // when then
        Assertions.assertThatThrownBy(() -> createOrderUseCase.execute(userId, items))
                .hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
    }
}