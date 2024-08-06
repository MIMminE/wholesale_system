package nuts.project.wholesale_system.order.domain.service.usecase.create;

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

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;

class CreateOrderUseCaseTest extends UseCaseTestSupport {

    @DisplayName("주문 상품 정보 리스트와 유저 아이디로 주문 생성 로직을 수행하고 결과를 반환한다.")
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
}