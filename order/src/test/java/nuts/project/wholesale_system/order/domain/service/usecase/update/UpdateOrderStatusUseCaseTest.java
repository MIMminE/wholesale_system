package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.domain.exception.StockException;
import nuts.project.wholesale_system.order.domain.model.OrderStatus;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
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
import static nuts.project.wholesale_system.order.domain.exception.StockException.StockExceptionCase.STOCK_SERVICE_FAIL;
import static org.mockito.ArgumentMatchers.any;


class UpdateOrderStatusUseCaseTest extends UseCaseTestSupport {

    @Commit
    @DisplayName("updateOrderStatusUseCase 성공 테스트")
    @Test
    void updateOrderStatusUseCaseSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        BDDMockito.given(stockService.returnStock(any(StockRequest.class)))
                .willReturn(Optional.of(new StockResponse(StockRequestType.Return, true)));

        // when
        UpdateOrderStatusDto statusDto = updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus));

        // then
        Assertions.assertThat(statusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, orderEntity.getOrderStatus().toString(), updateOrderStatus);

    }

    @DisplayName("updateOrderStatusUseCase 주문 취소 시 재고 반환 로직 호출 성공 테스트")
    @Test
    void updateOrderStatusUseCaseOrderCancelSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.cancelled.toString();

        BDDMockito.given(stockService.returnStock(any(StockRequest.class)))
                .willReturn(Optional.of(new StockResponse(StockRequestType.Return, true)));

        // when
        UpdateOrderStatusDto statusDto = updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus));

        // when
        Assertions.assertThat(statusDto)
                .extracting("orderId", "beforeOrderStatus", "afterOrderStatus")
                .contains(orderId, orderEntity.getOrderStatus().toString(), updateOrderStatus);
    }

    @DisplayName("updateOrderStatusUseCase 예외 발생 테스트 : 주문 취소 시 재고 시스템과 통신에 실패할 때")
    @Test
    void updateOrderStatusUseCaseStockException() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.cancelled.toString();

        BDDMockito.given(stockService.returnStock(any(StockRequest.class)))
                .willThrow(new StockException(STOCK_SERVICE_FAIL));

        // when then
        Assertions.assertThatThrownBy(() -> updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus)))
                .hasMessage(STOCK_SERVICE_FAIL.getMessage());
    }


    @DisplayName("updateOrderStatusUseCase 예외 발생 테스트 : 입력된 주문번호에 해당하는 주문이 없을때")
    @Test
    void updateOrderStatusUseCaseException() {
        // given
        OrderEntity orderEntity = getOrderedObject(OrderEntity.class).get(0);
        String orderId = orderEntity.getOrderId();

        String updateOrderStatus = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)].toString();

        // when then
        Assertions.assertThatThrownBy(() -> updateOrderStatusUseCase.execute(orderId, OrderStatus.valueOf(updateOrderStatus)))
                .hasMessage(UPDATE_NO_SUCH_ELEMENT.getMessage());
    }
}