package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.support.ExtendsFixtureRestDocsSupport;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.CreateStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.DeleteStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.UpdateStockRequest;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.StockService;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.usecase.create.CreateStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.delete.DeleteStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.get.GetStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.AddStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.DeductStockUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static java.util.Map.*;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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

    ObjectMapper mapper = new ObjectMapper();

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
    void createStock() throws Exception {
        // given
        CreateStockRequest createStockRequest = getOrderedObject(CreateStockRequest.class).get(0);

        String stockName = createStockRequest.getStockName();
        String category = createStockRequest.getCategory();

        String stockId = UUID.randomUUID().toString();


        BDDMockito.given(createStockUseCase.execute(ArgumentMatchers.anyString(), eq(stockName), eq(StockCategory.valueOf(category))))
                .willReturn(new CreateStockResultDto(stockId, stockName, category));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.post("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createStockRequest)));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(
                ofEntries(
                        entry("stockId", stockId),
                        entry("stockName", stockName),
                        entry("category", category)
                )));
    }


    @Test
    @DisplayName("PUT /stocks/add : stock 재고 추가 성공 테스트")
    void addStock() throws Exception {
        // given
        Stock stock = getOrderedObject(Stock.class).get(0);
        UpdateStockRequest updateStockRequest = getOrderedObject(UpdateStockRequest.class).get(0);

        String stockId = updateStockRequest.getStockId();
        changeFieldValue(stock, "stockId", stockId);
        int addQuantity = updateStockRequest.getQuantity();

        int quantity = stock.getQuantity();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();

        BDDMockito.given(addStockUseCase.execute(stockId, addQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity + addQuantity));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.put("/stocks/add")
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
        ;
    }

    @Test
    @DisplayName("PUT /stocks/deduct : stock 재고 차감 성공 테스트")
    void deductStock() throws Exception {

        // given
        Stock stock = getOrderedObject(Stock.class).get(0);
        UpdateStockRequest updateStockRequest = getOrderedObject(UpdateStockRequest.class).get(0);
        changeFieldValue(updateStockRequest, "quantity", new Random().nextInt(stock.getQuantity()));

        String stockId = updateStockRequest.getStockId();
        changeFieldValue(stock, "stockId", stockId);
        int deductQuantity = updateStockRequest.getQuantity();

        int quantity = stock.getQuantity();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();

        BDDMockito.given(deductStockUseCase.execute(stockId, deductQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity - deductQuantity));

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.put("/stocks/deduct")
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
        ;
    }


    @Test
    @DisplayName("DELETE /stocks : stock 삭제 성공 테스트")
    void deleteStock() throws Exception {

        // given
        DeleteStockRequest deleteStockRequest = getOrderedObject(DeleteStockRequest.class).get(0);

        String stockId = deleteStockRequest.getStockId();

        BDDMockito.willDoNothing().given(deleteStockUseCase).execute(stockId);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.delete("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deleteStockRequest)));

        // then
        verify(deleteStockUseCase).execute(stockId);

        resultActions
                .andDo(print())
                .andExpectAll(MockMvcSupport.mapMatchers(
                        ofEntries(
                                entry("requestStockId", stockId),
                                entry("result", true)
                        )))
        ;
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