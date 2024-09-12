package nuts.project.wolesale_system.stock.domain.service;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.domain.model.Stock;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import nuts.project.wolesale_system.stock.domain.service.usecase.create.CreateStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.delete.DeleteStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.get.GetStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.AddStockUseCase;
import nuts.project.wolesale_system.stock.domain.service.usecase.update.DeductStockUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class StockServiceTest extends FixtureGenerateSupport {

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

//    @Test
    @DisplayName("createStock 메서드로 createStockUseCase 정상 호출")
    void createStock() {

        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();

        BDDMockito.given(createStockUseCase.execute(any(), eq(stockName), eq(category)))
                .willReturn(new CreateStockResultDto(stockId, stockName, category.name()));

        // when
        CreateStockResultDto createResult = stockService.createStock(stockName, category.name());

        // then
        Assertions.assertThat(createResult)
                .extracting("stockId", "stockName", "category")
                .contains(stockId, stockName, category.name());
    }

//    @Test
    @DisplayName("deleteStock 메서드로 deleteStockUseCase 정상 호출")
    void deleteStock() {
        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();

        BDDMockito.willDoNothing().given(deleteStockUseCase).execute(stockId);

        // when
        stockService.deleteStock(stockId);

        // then
        BDDMockito.then(deleteStockUseCase).should().execute(stockId);
    }

//    @Test
    @DisplayName("getStock 메서드로 getStockUseCase 정상 호출")
    void getStock() {
        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        int quantity = stock.getQuantity();

        BDDMockito.given(getStockUseCase.execute(stockId))
                .willReturn(new GetStockResultDto(stockId, stockName, category.name(), quantity));

        // when
        GetStockResultDto getResult = stockService.getStock(stockId);

        // then
        Assertions.assertThat(getResult)
                .extracting("stockId", "stockName", "category", "quantity")
                .contains(stockId, stockName, category.name(), quantity);
    }

//    @Test
    @DisplayName("deductStock 메서드로 deductStockUseCase 정상 호출")
    void deductStock() {
        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        int quantity = stock.getQuantity();
        int deductQuantity = new Random().nextInt(quantity);

        BDDMockito.given(deductStockUseCase.execute(stockId, deductQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity - deductQuantity));

        // when
        UpdateStockResultDto deductResult = stockService.deductStock(stockId, deductQuantity);

        // then
        Assertions.assertThat(deductResult)
                .extracting("stockId", "stockName", "category", "beforeQuantity", "afterQuantity")
                .contains(stockId, stockName, category.name(), quantity, quantity - deductQuantity);

    }

//    @Test
    @DisplayName("addStock 메서드로 addStockUseCase 정상 호출")
    void addStock() {
        // given
        Stock stock = getOrderedObject(Stock.class).get(0);

        String stockId = stock.getStockId();
        String stockName = stock.getStockName();
        StockCategory category = stock.getCategory();
        int quantity = stock.getQuantity();
        int addQuantity = new Random().nextInt(quantity);

        BDDMockito.given(addStockUseCase.execute(stockId, addQuantity))
                .willReturn(new UpdateStockResultDto(stockId, stockName, category.name(), quantity, quantity + addQuantity));

        // when
        UpdateStockResultDto deductResult = stockService.addStock(stockId, addQuantity);

        // then
        Assertions.assertThat(deductResult)
                .extracting("stockId", "stockName", "category", "beforeQuantity", "afterQuantity")
                .contains(stockId, stockName, category.name(), quantity, quantity + addQuantity);

    }

    @Override
    protected List<OrderSheet> ordersObject() {

        return List.of(
                OrderSheet.order(orderCustom(Stock.class)
                                .set("stockName", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                                .set("quantity", Arbitraries.integers().between(10, 50))
                        , 3)
        );
    }
}