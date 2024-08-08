package nuts.project.wholesale_system.order.adapter.outbound.stock;

import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.domain.exception.OrderException;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequestType;
import nuts.project.wholesale_system.order.domain.ports.stock.StockResponse;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockDeductRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.request.StockReturnRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class StockServiceAdapterTest {

    FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(StockDeductRequest.class).minSize("items", 1), 1),
                    OrderSheet.order(orderCustom(StockReturnRequest.class).minSize("requestItems", 1), 1)
            )
    );

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    StockServicePort stockService;

    @DisplayName("재고 차감 요청에서 재고 서비스와 통신이 실패할 경우 예외가 발생한다.")
    @Test
    void deductStockSeverException() {
        // given
        StockDeductRequest stockDeductRequest = fixtureManager.getOrderObject(StockDeductRequest.class);

        BDDMockito.given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(Map.class)))
                .willThrow(ResourceAccessException.class);

        // when then
        Assertions.assertThatThrownBy(() -> stockService.deductStock(stockDeductRequest))
                .hasMessage(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL.getMessage());
    }


    @DisplayName("재고 차감 요청이 성공적으로 수행되고 결과를 반환한다.")
    @Test
    void deductStockSuccess() {
        // given
        StockDeductRequest stockDeductRequest = fixtureManager.getOrderObject(StockDeductRequest.class);

        BDDMockito.given(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(new HttpEntity<>(stockDeductRequest)), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("result", true)));

        // when
        StockResponse stockResponse = stockService.deductStock(stockDeductRequest);

        // then
        Assertions.assertThat(stockResponse).extracting("responseType", "result")
                .contains(StockRequestType.Deduct, true);
    }

    @DisplayName("재고 차감 요청에서 현재 가진 재고보다 많은 수량이 요청된 경우 예외가 발생한다.")
    @Test
    void deductStockOutOfException() {
        // given
        StockDeductRequest stockDeductRequest = fixtureManager.getOrderObject(StockDeductRequest.class);

        BDDMockito.given(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(new HttpEntity<>(stockDeductRequest)), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("result", false)));

        // when then
        Assertions.assertThatThrownBy(() -> stockService.deductStock(stockDeductRequest))
                .hasMessage(OrderException.OrderExceptionCase.OUT_OF_STOCK.getMessage());
    }

    @DisplayName("재고 반환 요청에서 재고 서비스와 통신이 실패할 경우 예외가 발생한다.")
    @Test
    void returnStockSeverException() {
        // given
        StockReturnRequest stockReturnRequest = fixtureManager.getOrderObject(StockReturnRequest.class);

        BDDMockito.given(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(Map.class)))
                .willThrow(ResourceAccessException.class);

        // when then
        Assertions.assertThatThrownBy(() -> stockService.returnStock(stockReturnRequest))
                .hasMessage(OrderException.OrderExceptionCase.STOCK_SERVICE_FAIL.getMessage());
    }

    @DisplayName("재고 반환 요청이 성공적으로 수행되고 결과를 반환한다.")
    @Test
    void returnStockSuccess() {
        // given
       StockReturnRequest stockReturnRequest = fixtureManager.getOrderObject(StockReturnRequest.class);

        BDDMockito.given(restTemplate.exchange(anyString(), any(HttpMethod.class), eq(new HttpEntity<>(stockReturnRequest)), eq(Map.class)))
                .willReturn(ResponseEntity.ok(Map.of("result", true)));

        // when
        StockResponse stockResponse = stockService.returnStock(stockReturnRequest);

        // then
        Assertions.assertThat(stockResponse).extracting("responseType", "result")
                .contains(StockRequestType.Return, true);
    }

}