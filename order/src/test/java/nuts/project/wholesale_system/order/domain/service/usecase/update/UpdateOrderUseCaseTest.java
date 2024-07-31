package nuts.project.wholesale_system.order.domain.service.usecase.update;

import nuts.project.wholesale_system.order.adapter.outbound.repository.order.OrderEntity;
import nuts.project.wholesale_system.order.adapter.outbound.repository.order_item.OrderItemEntity;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.service.dto.UpdateOrderDto;
import nuts.project.wholesale_system.order.support.UseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;

import java.util.List;
import java.util.Optional;

import static nuts.project.wholesale_system.order.domain.exception.OrderException.OrderExceptionCase.UPDATE_NO_SUCH_ELEMENT;

class UpdateOrderUseCaseTest extends UseCaseTestSupport {

    @DisplayName("updateOrderUseCase 성공 테스트")
    @Test
    void updateOrderUseCaseSuccess() {
        // given
        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
        List<OrderItem> updateOrderItemsSource = getOrderedObject(OrderItem.class);

        String orderId = orderEntity.getOrderId();
        List<OrderItem> beforeOrderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();

        BDDMockito.given(stockService.updateStock(ArgumentMatchers.isA(StockRequest.class)))
                .willReturn(Optional.of(new StockResponse(StockRequestType.Update, true)));

        // when
        UpdateOrderDto result = updateOrderUseCase.execute(orderId, updateOrderItemsSource);

        // then
        Assertions.assertThat(result)
                .extracting("orderId", "userId")
                .contains(orderId, orderEntity.getUserId());

        Assertions.assertThat(result.getBeforeOrderItems())
                .extracting("productId", "quantity")
                .contains(beforeOrderItems.stream()
                        .map(e -> Tuple.tuple(e.getProductId(), e.getQuantity())).toArray(Tuple[]::new));

        Assertions.assertThat(result.getAfterOrderItems())
                .extracting("productId", "quantity")
                .contains(updateOrderItemsSource.stream()
                        .map(e -> Tuple.tuple(e.getProductId(), e.getQuantity())).toArray(Tuple[]::new));
    }

    @DisplayName("updateOrderUseCase Order 예외 발생 테스트 : 입력된 주문번호에 해당하는 주문이 없을때")
    @Test
    void updateOrderUseCaseOrderException() {
        // given
        OrderEntity orderEntity = getOrderedObject(OrderEntity.class).get(0);
        String orderId = orderEntity.getOrderId();
        List<OrderItem> orderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();

        // when then
        Assertions.assertThatThrownBy(() -> updateOrderUseCase.execute(orderId, orderItems))
                .hasMessage(UPDATE_NO_SUCH_ELEMENT.getMessage());

    }

//    @DisplayName("updateOrderUseCase Stock 예외 발생 테스트 : 변경한 상품의 수량이 부족한 경우")
//    @Test
//    void updateOrderUseCaseStockException() {
//        // given
//        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
//        String orderId = orderEntity.getOrderId();
//        List<OrderItem> orderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();
//
//        BDDMockito.given(stockService.updateStock(ArgumentMatchers.isA(StockRequest.class)))
//                .willThrow(new StockException(OUT_OF_STOCK));
//
//        // when then
//        Assertions.assertThatThrownBy(() -> updateOrderUseCase.execute(orderId, orderItems))
//                .hasMessage(OUT_OF_STOCK.getMessage());

//    }

//    @DisplayName("updateOrderUseCase Stock 예외 발생 테스트 : 재고 서비스와의 통신에 실패한 경우")
//    @Test
//    void updateOrderUseCaseFailException() {
//        // given
//        OrderEntity orderEntity = orderRepository.save(getOrderedObject(OrderEntity.class).get(0));
//        String orderId = orderEntity.getOrderId();
//        List<OrderItem> orderItems = orderEntity.getItems().stream().map(OrderItemEntity::toOrderItem).toList();
//
//        BDDMockito.given(stockService.updateStock(ArgumentMatchers.isA(StockRequest.class)))
//                .willThrow(new StockException(STOCK_SERVICE_FAIL));
//
//        // when then
//        Assertions.assertThatThrownBy(() -> updateOrderUseCase.execute(orderId, orderItems))
//                .hasMessage(STOCK_SERVICE_FAIL.getMessage());
//    }
}