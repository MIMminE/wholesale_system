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

    @DisplayName("재고 id를 입력받아 해당되는 id에 대한 데이터가 있다면 재고를 삭제한다.")
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

    @DisplayName("입력받은 재고 id에 해당되는 데이터가 없어 재고 데이터 삭제에 실패하면 예외를 던진다.")
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