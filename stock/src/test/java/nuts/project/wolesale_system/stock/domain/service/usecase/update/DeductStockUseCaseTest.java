package nuts.project.wolesale_system.stock.domain.service.usecase.update;

import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import nuts.project.wolesale_system.stock.support.StockUseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.NOT_FOUND_ELEMENT;
import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.OUT_OF_STOCK;

class DeductStockUseCaseTest extends StockUseCaseTestSupport {
    @DisplayName("deductStockUseCase 동작 성공 테스트")
    @Test
    void deductStockUseCaseSuccess() {
        // given
        StockEntity stockEntity = stockRepository.save(getOrderedObject(StockEntity.class).get(0));
        String stockId = stockEntity.getStockId();
        String stockName = stockEntity.getStockName();
        StockCategory category = stockEntity.getCategory();
        int quantity = stockEntity.getQuantity();

        int requestQuantity = new Random().nextInt(quantity);

        // when
        UpdateStockResultDto deductResult = deductStockUseCase.execute(stockId, requestQuantity);

        // then
        Assertions.assertThat(deductResult)
                .extracting("stockId", "stockName", "category", "beforeQuantity", "afterQuantity")
                .contains(stockId, stockName, category.name(), quantity, quantity - requestQuantity);

    }


    @DisplayName("deductStockUseCase 예외 테스트 : 입력한 stockId에 해당하는 재고 데이터가 없을 때")
    @Test
    void deductStockUseCaseNotFoundStockId() {
        // given
        StockEntity stockEntity = getOrderedObject(StockEntity.class).get(0);
        String stockId = stockEntity.getStockId();

        int requestQuantity = new Random().nextInt(50);

        // when then
        Assertions.assertThatThrownBy(() -> deductStockUseCase.execute(stockId, requestQuantity))
                .hasMessage(NOT_FOUND_ELEMENT.getMessage());
    }

    @DisplayName("deductStockUseCase 예외 테스트 : 요청 개수보다 남아있는 재고가 적을 때")
    @Test
    void deductStockUseCaseQuantityException() {
        // given
        StockEntity stockEntity = stockRepository.save(getOrderedObject(StockEntity.class).get(0));
        String stockId = stockEntity.getStockId();
        int quantity = stockEntity.getQuantity();

        int requestQuantity = quantity + 1;

        // when then
        Assertions.assertThatThrownBy(() -> deductStockUseCase.execute(stockId, requestQuantity))
                .hasMessage(OUT_OF_STOCK.getMessage());

    }
}