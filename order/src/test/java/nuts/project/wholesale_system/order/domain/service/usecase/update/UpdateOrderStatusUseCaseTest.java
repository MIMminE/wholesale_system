package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderStatusDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.Random;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT;
import static org.mockito.ArgumentMatchers.any;


class UpdateOrderStatusUseCaseTest extends UseCaseTestSupport {

    @Commit
    @DisplayName("주문 번호에 해당하는 주문의 상태를 수정하고 결과를 반환한다.")
    @Test
    void updateOrderStatusUseCaseSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        BDDMockito.given(stockService.returnStock(any(StockReturnRequest.class)))
                .willReturn(new StockResponse(StockRequestType.Return, true));

        // when
        UpdateOrderStatusDto statusDto = updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus));

        // then
        Assertions.assertThat(statusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, orderEntity.getOrderStatus().toString(), updateOrderStatus);

    }

    @DisplayName("주문 상태 변경 요청 정보의 주문번호에 해당하는 주문이 없을 경우 예외를 던진다.")
    @Test
    void updateOrderStatusUseNotFoundOrderId() {
        // given
        OrderEntity orderEntity = getOrderedObject(OrderEntity.class).get(0);
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        // when then
        Assertions.assertThatThrownBy(() -> updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus)))
                .hasMessage(UPDATE_NO_SUCH_ELEMENT.getMessage());
    }
}