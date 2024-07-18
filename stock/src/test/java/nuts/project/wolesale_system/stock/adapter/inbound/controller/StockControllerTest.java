package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.support.ExtendsFixtureRestDocsSupport;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.CreateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.DeleteStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.UpdateStockRequest;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.StockService;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.usecase.create.CreateStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.delete.DeleteStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.get.GetStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.AddStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.DeductStockUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class StockControllerTest extends ExtendsFixtureRestDocsSupport {

    @Mock
    CreateStockUseCase createStockUseCase;

    @Mock
    DeleteStockUseCase deleteStockUseCase;

    @Mock
    GetStockUseCase getStockUseCase;

    @Mock
    DeductStockUseCase deductStockUseCase;

    @Mock
    AddStockUseCase addStockUseCase;

    @InjectMocks
    StockService stockService;

    @Test
    @DisplayName("GET /stocks/{stockId} : stockId 정보 조회 성공 테스트")
    void getStock() throws Exception {

        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        int quantity = stock.getQuantity();

        BDDMockito.given(getStockUseCase.execute(stockId))
                .willReturn(new GetStockResultDto(stockId, stockName, category.name(), quantity));


        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.get("/stocks/{stockId}", stockId));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(ofEntries(
                entry("stockId", stockId),
                entry("stockName", stockName),
                entry("category", category.name()),
                entry("quantity", quantity)
        )));


    }

    @Test
    @DisplayName("POST /stocks : stock 생성 성공 테스트")
    void createStock() {
    }


    @Test
    @DisplayName("PUT /stocks/add : stock 재고 추가 성공 테스트")
    void addStock() {

    }

    @Test
    @DisplayName("PUT /stocks/deduct : stock 재고 차감 성공 테스트")
    void deductStock() {
    }


    @Test
    @DisplayName("DELETE /stocks : stock 삭제 성공 테스트")
    void deleteStock() {
    }


    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(orderCustom(Stock.class)
                                .set("stockId", UUID.randomUUID().toString())
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("quantity", Arbitraries.integers().between(10, 50))
                        , 1),
                OrderSheet.order(orderCustom(CreateStockRequest.class)
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("category", Arbitraries.of(Arrays.stream(StockCategory.values()).map(Enum::toString).toArray(String[]::new)))
                        , 1),
                OrderSheet.order(orderCustom(DeleteStockRequest.class)
                                .set("stockId", UUID.randomUUID().toString())
                        , 1),
                OrderSheet.order(orderCustom(UpdateStockRequest.class)
                                .set("stockId", UUID.randomUUID().toString())
                                .set("quantity", Arbitraries.integers().between(1, 10))
                        , 1)
        );
    }

    @Override
    protected Object initController() {
        return new StockController(stockService);
    }
}