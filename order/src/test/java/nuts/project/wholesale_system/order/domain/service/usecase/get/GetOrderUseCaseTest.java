package nuts.project.wholesale_system.order.domain.service.usecase.get;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.service.dto.OrderProcessDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.GET_NO_SUCH_ELEMENT;


class GetOrderUseCaseTest extends UseCaseTestSupport {
//    @DisplayName("주문 번호에 해당하는 주문 정보를 반환한다.")
//    @Test
//    void getOrderUseCaseSuccess() {
//        // given
//        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
//        String orderId = orderEntity.getOrderId();
//
//
//        BDDMockito.given(paymentService.getPaymentInformation(orderId))
//                .willReturn(new PaymentResponse("testAccount", "testName", 15000));
//
//        // when
//        OrderProcessDto orderProcessDto = getOrderUseCase.execute(orderId);
//
//        // then
//        Assertions.assertThat(orderProcessDto)
//                .extracting("paymentInformation.payInfo", "order.orderId", "order.userId")
//                .contains("testAccount", orderId, orderEntity.getUserId());
//
//    }

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
}