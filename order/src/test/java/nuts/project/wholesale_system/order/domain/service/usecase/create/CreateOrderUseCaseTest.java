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
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

class CreateOrderUseCaseTest extends UseCaseTestSupport {

    @DisplayName("상품 주문 정보를 기반으로 재고 서비스" +
            "" +
            "상품 정보 기반으로 인증, 재고 서비스에게 검증 요청이 성공적일 경우 주문을 생성하고 결과를 반환한다.")
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

//    @DisplayName("createOrderUseCase 예외 발생 테스트 : 결제 시스템과의 통신에 실패한 경우")
//    @Test
//    void createOrderUseCaseException() {
//
//        // given
//        Order order = getOrderedObject(Order.class).get(0);
//        String userId = order.getUserId();
//        List<OrderItem> items = order.getItems();
//
//        BDDMockito.given(paymentService.requestPayment(eq(userId), anyString()))
//                .willThrow(new PaymentException(PAYMENT_SERVICE_FAIL));
//
//        // when then
//        Assertions.assertThatThrownBy(() -> createOrderUseCase.execute(userId, items))
//                .hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
//    }
}