package nuts.project.wolesale_system.stock.domain.service.usecase.get;

import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import nuts.project.wolesale_system.stock.support.StockUseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.NOT_FOUND_ELEMENT;


class GetStockUseCaseTest extends StockUseCaseTestSupport {

    @DisplayName("재고 id를 입력받아 해당되는 id에 대한 데이터를 조회하고 결과를 반환한다.")
    @Test
    void getStockUseCaseSuccess() {
        // given
        StockEntity stockEntity = stockRepository.save(getOrderedObject(StockEntity.class).get(0));
        String stockId = stockEntity.getStockId();
        String stockName = stockEntity.getStockName();
        StockCategory category = stockEntity.getCategory();
        int quantity = stockEntity.getQuantity();

        // when
        GetStockResultDto getResult = getStockUseCase.execute(stockId);

        // then
        Assertions.assertThat(getResult)
                .extracting("stockId","stockName","category","quantity")
                .contains(stockId, stockName, category.name(), quantity);

    }

    @DisplayName("입력받은 재고 id에 해당되는 데이터가 없으면 예외를 던진다.")
    @Test
    void getStockUseCaseNotFoundStockId() {
        // given
        StockEntity stockEntity = getOrderedObject(StockEntity.class).get(0);
        String stockId = stockEntity.getStockId();

        // when then
        Assertions.assertThatThrownBy(() -> getStockUseCase.execute(stockId))
                .hasMessage(NOT_FOUND_ELEMENT.getMessage());
    }

}