package nuts.project.wolesale_system.stock.adapter.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.DeleteStockRequest;
import nuts.project.wolesale_system.stock.adapter.inbound.controller.request.UpdateStockRequest;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.NOT_FOUND_ELEMENT;
import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.OUT_OF_STOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@WebMvcTest(StockController.class)
public class StockControllerExceptionTest extends FixtureGenerateSupport {

    @MockBean
    StockService stockService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("stock 컨트롤러 삭제 요청 예외 : 요청한 데이터가 없을 때")
    void deleteStockRequestNotFoundException() throws Exception {
        // given
        DeleteStockRequest deleteStockRequest = getOrderedObject(DeleteStockRequest.class).get(0);

        String stockId = deleteStockRequest.getStockId();

        BDDMockito.doThrow(new StockException(NOT_FOUND_ELEMENT)).when(stockService).deleteStock(stockId);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deleteStockRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ELEMENT.getMessage()));
    }

    @Test
    @DisplayName("stock 컨트롤러 조회 요청 예외 : 요청한 데이터가 없을 때")
    void getStockRequestNotFoundException() throws Exception {
        // given
        String stockId = UUID.randomUUID().toString();

        BDDMockito.doThrow(new StockException(NOT_FOUND_ELEMENT)).when(stockService).getStock(stockId);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/stocks/{stockId}", stockId));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ELEMENT.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("stock 컨트롤러 재고 추가 요청 예외 : 요청한 재고가 존재하지 않을 때")
    void addStockRequestNotFoundException() throws Exception {
        // given
        UpdateStockRequest updateStockRequest = getOrderedObject(UpdateStockRequest.class).get(0);
        String stockId = updateStockRequest.getStockId();
        int quantity = updateStockRequest.getQuantity();

        BDDMockito.doThrow(new StockException(NOT_FOUND_ELEMENT)).when(stockService).addStock(stockId, quantity);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/stocks/add", stockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateStockRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ELEMENT.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("stock 컨트롤러 재고 감소 요청 예외 : 요청한 재고가 존재하지 않을 때")
    void deductStockRequestNotFoundException() throws Exception {
        // given
        UpdateStockRequest updateStockRequest = getOrderedObject(UpdateStockRequest.class).get(0);
        String stockId = updateStockRequest.getStockId();
        int quantity = updateStockRequest.getQuantity();

        BDDMockito.doThrow(new StockException(NOT_FOUND_ELEMENT)).when(stockService).deductStock(stockId, quantity);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/stocks/deduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateStockRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NOT_FOUND_ELEMENT.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("stock 컨트롤러 재고 감소 요청 예외 : 요청 수량이 남은 수량보다 많을 때")
    void deductStockRequestOutOfStockException() throws Exception {
        // given
        UpdateStockRequest updateStockRequest = getOrderedObject(UpdateStockRequest.class).get(0);
        String stockId = updateStockRequest.getStockId();
        int quantity = updateStockRequest.getQuantity();

        BDDMockito.doThrow(new StockException(OUT_OF_STOCK)).when(stockService).deductStock(stockId, quantity);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/stocks/deduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateStockRequest)));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(OUT_OF_STOCK.getMessage()))
                .andDo(print());
    }

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(orderCustom(Stock.class)
                                .set("stockId", UUID.randomUUID().toString())
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("quantity", Arbitraries.integers().between(10, 50))
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
}
