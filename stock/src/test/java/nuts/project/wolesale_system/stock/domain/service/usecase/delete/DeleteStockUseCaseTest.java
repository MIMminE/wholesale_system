package nuts.project.wolesale_system.stock.domain.service.usecase.delete;

import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.support.StockUseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.NOT_FOUND_ELEMENT;


class DeleteStockUseCaseTest extends StockUseCaseTestSupport {

    @DisplayName("deleteStockUseCase 동작 성공 테스트")
    @Test
    void deleteStockUseCaseSuccess() {
        // given
        StockEntity stockEntity = stockRepository.save(getOrderedObject(StockEntity.class).get(0));
        String stockId = stockEntity.getStockId();

        // when
        deleteStockUseCase.execute(stockId);

        // then
        Assertions.assertThatThrownBy(() -> stockRepository.findById(stockId).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("deleteStockUseCase 예외 테스트 : 입력한 stockId에 해당하는 재고 데이터가 없을 때")
    @Test
    void deleteStockUseCaseNotFoundStockId() {
        // given
        StockEntity stockEntity = getOrderedObject(StockEntity.class).get(0);
        String stockId = stockEntity.getStockId();

        // when then
        Assertions.assertThatThrownBy(() -> deleteStockUseCase.execute(stockId))
                .hasMessage(NOT_FOUND_ELEMENT.getMessage());
    }
}