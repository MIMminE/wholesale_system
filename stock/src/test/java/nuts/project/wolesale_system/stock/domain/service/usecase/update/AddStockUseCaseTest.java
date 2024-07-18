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

class AddStockUseCaseTest extends StockUseCaseTestSupport {

    @DisplayName("addStockUseCase 동작 성공 테스트")
    @Test
    void addStockUseCaseSuccess() {
        // given
        StockEntity stockEntity = stockRepository.save(getOrderedObject(StockEntity.class).get(0));
        String stockId = stockEntity.getStockId();
        String stockName = stockEntity.getStockName();
        StockCategory category = stockEntity.getCategory();
        int quantity = stockEntity.getQuantity();

        int requestQuantity = new Random().nextInt(50);

        // when
        UpdateStockResultDto addResult = addStockUseCase.execute(stockId, requestQuantity);

        // then
        Assertions.assertThat(addResult)
                .extracting("stockId", "stockName", "category", "beforeQuantity", "afterQuantity")
                .contains(stockId, stockName, category.name(), quantity, quantity + requestQuantity);

    }

    @DisplayName("addStockUseCase 예외 테스트 : 입력한 stockId에 해당하는 재고 데이터가 없을 때")
    @Test
    void addStockUseCaseNotFoundStockId() {
        // given
        StockEntity stockEntity = getOrderedObject(StockEntity.class).get(0);
        String stockId = stockEntity.getStockId();

        int requestQuantity = new Random().nextInt(50);

        // when then
        Assertions.assertThatThrownBy(() -> addStockUseCase.execute(stockId, requestQuantity))
                .hasMessage(NOT_FOUND_ELEMENT.getMessage());
    }

}