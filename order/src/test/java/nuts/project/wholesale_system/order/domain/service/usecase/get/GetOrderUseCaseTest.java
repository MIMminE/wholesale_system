package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT;


class GetOrderUseCaseTest extends UseCaseTestSupport {
    @DisplayName("인정 정보 기반으로 인증 서비스 검증 요청에 성공할 경우 주문 번호에 해당하는 주문 정보를 반환한다.")
    @Test
    void getOrderUseCaseSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();


        BDDMockito.given(paymentService.getPaymentInformation(orderId))
                .willReturn(new PaymentResponse("testAccount", "testName", 15000));

        // when
        OrderProcessDto orderProcessDto = getOrderUseCase.execute(orderId);

        // then
        Assertions.assertThat(orderProcessDto)
                .extracting("paymentInformation.payInfo", "order.orderId", "order.userId")
                .contains("testAccount", orderId, orderEntity.getUserId());

    }

    @DisplayName("주문번호에 해당하는 주문이 없을 경우 예외를 던진다.")
    @Test
    void getOrderUseCaseOrderIdNotFoundOrderId() {
        // given
        OrderEntity orderEntity = getOrderedObject(OrderEntity.class).get(0);
        String orderId = orderEntity.getOrderId();

        // when then
        Assertions.assertThatThrownBy(() -> getOrderUseCase.execute(orderId))
                .hasMessage(GET_NO_SUCH_ELEMENT.getMessage());

    }

//    @DisplayName("getOrderUseCase 예외 발생 테스트 : 결제 시스템과 통신에 실패할 때")
//    @Test
//    void getOrderUseCasePaymentException() {
//        // given
//        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
//        String orderId = orderEntity.getOrderId();
//
//
//        BDDMockito.given(paymentService.getPaymentInformation(orderId))
//                .willThrow(new PaymentException(PAYMENT_SERVICE_FAIL));
//
//        // when then
//        Assertions.assertThatThrownBy(() -> getOrderUseCase.execute(orderId))
//                .hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
//    }
}