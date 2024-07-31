package nuts.project.wholesale_system.order.domain.service.usecase.delete;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.model.Order;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.payment.PaymentResponse;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.List;
import java.util.UUID;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.DELETE_NO_SUCH_ELEMENT;
import static org.assertj.core.api.Assertions.*;

class DeleteOrderIdUseCaseTest extends UseCaseTestSupport {

    @DisplayName("deleteOrderIdUseCase 성공 테스트")
    @Test
    void deleteOrderIdUseCaseSuccess() {

        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String userId = orderEntity.getUserId();
        String orderId = orderEntity.getOrderId();
        List<OrderItem> orderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();

        PaymentResponse paymentResponse = new PaymentResponse(userId, "test", 1500);
        BDDMockito.given(paymentService.deletePaymentInformation(orderId))
                .willReturn(paymentResponse);

        // when
        Order result = deleteOrderIdUseCase.execute(orderId);

        // then
        assertThat(result)
                .extracting("orderId", "userId")
                .contains(orderId, userId);

        assertThat(result.getItems())
                .extracting("productId", "quantity")
                .contains(Tuple.tuple(orderItems.get(0).getProductId(), orderItems.get(0).getQuantity()),
                        Tuple.tuple(orderItems.get(1).getProductId(), orderItems.get(1).getQuantity()));
    }

    @DisplayName("deleteOrderIdUseCase 예외 발생 테스트 : 삭제하고자 하는 주문이 존재하지 않을 때")
    @Test
    void deleteOrderIdUseCaseUpdateException() {

        // given
        Order order = getOrderedObject(Order.class).get(0);
        String NonExistentOrderId = UUID.randomUUID().toString();

        // when then
        assertThatThrownBy(() -> deleteOrderIdUseCase.execute(NonExistentOrderId))
                .hasMessage(DELETE_NO_SUCH_ELEMENT.getMessage());
    }

//    @DisplayName("deleteOrderIdUseCase 예외 발생 테스트 : 결제 시스템과의 통신에 실패한 경우")
//    @Test
//    void deleteOrderIdUseCasePaymentServiceException() {
//        // given
//        OrderEntity saveEntity = getOrderedObject(OrderEntity.class).get(0);
//        OrderEntity orderEntity = orderRepository.save(saveEntity);
//        String userId = orderEntity.getUserId();
//        String orderId = orderEntity.getOrderId();
//
//        PaymentResponse paymentResponse = new PaymentResponse(userId, "test", 1500);
//        BDDMockito.given(paymentService.deletePaymentInformation(orderId))
//                .willThrow(new PaymentException(PAYMENT_SERVICE_FAIL));
//
//        // when then
//        assertThatThrownBy(() -> deleteOrderIdUseCase.execute(orderId))
//                .hasMessage(PAYMENT_SERVICE_FAIL.getMessage());
//    }
}