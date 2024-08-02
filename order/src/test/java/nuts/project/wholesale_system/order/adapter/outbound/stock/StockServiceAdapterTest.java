package nuts.project.wholesale_system.order.adapter.outbound.stock;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.order.domain.model.OrderItem;
import nuts.project.wholesale_system.order.domain.ports.stock.StockRequest;
import nuts.project.wholesale_system.order.domain.ports.stock.StockServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
//@ActiveProfiles("test")
@Profile("integration_test")
class StockServiceAdapterTest {

    @Autowired
    StockServicePort stockService;


    @DisplayName("재고 차감 요청 성공")
//    @Test
    @Commit
    void deductStockSuccess() {
        // given
        StockRequest stockRequest = new StockRequest(List.of(new OrderItem("172604a7-1620-4c8a-bd1e-78bdf92d3203", 1)));

        stockService.deductStock(stockRequest);
        // when

        // then
    }

    @DisplayName("재고 차감 요청 예외 : 재고보다 많은 수량을 요청했을 때")
//    @Test
    void deductStockOutOfException() {
        // given

        // when

        // then
    }

    @DisplayName("재고 차감 요청 예외 : 재고 서버와의 통신에 실패했을 때")
//    @Test
    void deductStockSeverException() {
        // given

        // when

        // then
    }



}