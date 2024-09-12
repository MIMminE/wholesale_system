package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.CreateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.DeleteStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.UpdateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.restdocs.RequestRestDocs;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.restdocs.ResponseRestDocs;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.StockService;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static nuts.lib.manager.fixture_manager.FixtureManager.changeFieldValue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
class StockControllerTest extends RestDocsSupport {

    FixtureManager fixtureManager = new FixtureManager(List.of(
            OrderSheet.order(FixtureManager.orderCustom(CreateStockRequest.class)
                    .set("stockName", Arbitraries.strings().alpha().ofMinLength(3))
                    .set("category", Arbitraries.of(Arrays.stream(StockCategory.values()).map(Objects::toString).toArray())), 1),

            OrderSheet.order(FixtureManager.orderCustom(DeleteStockRequest.class)
                    .set("stockId", UUID.randomUUID().toString()), 1),

            OrderSheet.order(FixtureManager.orderCustom(Stock.class)
                    .set("stockId", UUID.randomUUID().toString())
                    .set("stockName", Arbitraries.strings().alpha().ofMinLength(3))
                    .set("category", Arbitraries.of(StockCategory.values()))
                    .set("quantity", Arbitraries.integers().between(2, 10)), 1),

            OrderSheet.order(FixtureManager.orderCustom(UpdateStockRequest.class)
                    .set("stockId", UUID.randomUUID().toString())
                    .set("quantity", Arbitraries.integers().between(2, 10)), 1)
    ));

    RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);

    @Mock
    StockService stockService;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Get /stock-service/stocks/{stockId} 요청을 받아 StockService의 재고 조회 메서드를 호출하고 결과를 반환한다.")
    void getStock() throws Exception {

        // given
        Stock stock = fixtureManager.getOrderObject(Stock.class);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        int quantity = stock.getQuantity();

        BDDMockito.given(stockService.getStock(stockId))
                .willReturn(new GetStockResultDto(stockId, stockName, category.name(), quantity));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.get("/stock-service/stocks/{stockId}", stockId));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(ofEntries(
                entry("stockId", stockId),
                entry("stockName", stockName),
                entry("category", category.name()),
                entry("quantity", quantity)
        )))
//                .andDo(restDocsManager.document("get-stock", "getStock"))
        ;
    }

    @Test
    @DisplayName("Post /stock-service/stocks 요청을 받아 StockService의 재고 생성 메서드를 호출하고 결과를 반환한다.")
    void createStock() throws Exception {
        // given
        CreateStockRequest createStockRequest = fixtureManager.getOrderObject(CreateStockRequest.class);

        String stockName = createStockRequest.getStockName();
        String category = createStockRequest.getCategory();
        String stockId = UUID.randomUUID().toString();

        BDDMockito.given(stockService.createStock(stockName, category))
                .willReturn(new CreateStockResultDto(stockId, stockName, category));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.post("/stock-service/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createStockRequest)));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(
                ofEntries(
                        entry("stockId", stockId),
                        entry("stockName", stockName),
                        entry("category", category)
                )))
//                .andDo(restDocsManager.document("create-stock", "createStock", "createStock"))
        ;
    }


    @Test
    @DisplayName("Put /stock-service/add 요청을 받아 StockService의 재고 추가 메서드를 호출하고 결과를 반환한다.")
    void addStock() throws Exception {

        // given
        Stock stock = fixtureManager.getOrderObject(Stock.class);
        UpdateStockRequest updateStockRequest = fixtureManager.getOrderObject(UpdateStockRequest.class);

        String stockId = updateStockRequest.getStockId();
        changeFieldValue(stock, "stockId", stockId);
        int addQuantity = updateStockRequest.getQuantity();

        int quantity = stock.getQuantity();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        BDDMockito.given(stockService.addStock(stockId, addQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity + addQuantity));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.put("/stock-service/stocks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateStockRequest)));

        // then
        resultActions
                .andExpectAll(MockMvcSupport.mapMatchers(
                        ofEntries(
                                entry("stockId", stockId),
                                entry("stockName", stockName),
                                entry("category", category.name()),
                                entry("beforeQuantity", quantity),
                                entry("afterQuantity", quantity + addQuantity)
                        )))
//                .andDo(restDocsManager.document("add-stock", "addStock", "addStock"))
        ;
    }


    @Test
    @DisplayName("Put /stock-service/deduct 요청을 받아 StockService의 재고 삭제 메서드를 호출하고 결과를 반환한다.")
    void deductStock() throws Exception {

        // given
        Stock stock = fixtureManager.getOrderObject(Stock.class);
        UpdateStockRequest updateStockRequest = fixtureManager.getOrderObject(UpdateStockRequest.class);

        changeFieldValue(updateStockRequest, "quantity", new Random().nextInt(stock.getQuantity()));

        String stockId = updateStockRequest.getStockId();
        changeFieldValue(stock, "stockId", stockId);
        int deductQuantity = updateStockRequest.getQuantity();

        int quantity = stock.getQuantity();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();

        BDDMockito.given(stockService.deductStock(stockId, deductQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity - deductQuantity));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.put("/stock-service/stocks/deduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateStockRequest)));

        // then
        resultActions
                .andExpectAll(MockMvcSupport.mapMatchers(
                        ofEntries(
                                entry("stockId", stockId),
                                entry("stockName", stockName),
                                entry("category", category.name()),
                                entry("beforeQuantity", quantity),
                                entry("afterQuantity", quantity - deductQuantity)
                        )))
//                .andDo(restDocsManager.document("deduct-stock", "deductStock", "deductStock"))
        ;
    }


    @Test
    @DisplayName("Delete /stock-service/stocks 요청을 받아 StockService의 재고 삭제 메서드를 호출하고 결과를 반환한다.")
    void deleteStock() throws Exception {

        // given
        DeleteStockRequest deleteStockRequest = fixtureManager.getOrderObject(DeleteStockRequest.class);

        String stockId = deleteStockRequest.getStockId();

        BDDMockito.willDoNothing().given(stockService).deleteStock(stockId);
        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.delete("/stock-service/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deleteStockRequest)));

        // then
        verify(stockService).deleteStock(stockId);

        resultActions
                .andDo(print())
                .andExpectAll(MockMvcSupport.mapMatchers(
                        ofEntries(
                                entry("requestStockId", stockId),
                                entry("result", true)
                        )))
//                .andDo(restDocsManager.document("delete-stock", "deleteStock", "deleteStock"))
        ;
    }


    @Override
    protected Object initController() {
        return new StockController(stockService);
    }
}