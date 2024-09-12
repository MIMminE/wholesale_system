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

    @DisplayName("재고 id와 추가 수량을 입력받아 해당되는 재고의 수량을 증가시키고 결과를 반환한다.")
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

    @DisplayName("입력받은 재고 id에 해당되는 데이터가 없어 재고 추가에 실패하면 예외를 던진다.")
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