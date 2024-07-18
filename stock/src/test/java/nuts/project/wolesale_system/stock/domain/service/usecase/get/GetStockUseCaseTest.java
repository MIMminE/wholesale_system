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

    @DisplayName("getStockUseCase 동작 성공 테스트")
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

    @DisplayName("getStockUseCase 예외 테스트 : 입력한 stockId에 해당하는 재고 데이터가 없을 때")
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