package nuts.project.wolesale_system.stock.domain.service.usecase.create;

import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import nuts.project.wolesale_system.stock.support.StockUseCaseTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CreateStockUseCaseTest extends StockUseCaseTestSupport {

    @DisplayName("재고 id, 재고 이름, 카테고리값을 입력 받아 재고를 생성하고 결과를 반환한다.")
    @Test
    void createStockUseCaseSuccess() {
        // given
        StockEntity stockEntity = getOrderedObject(StockEntity.class).get(0);
        String stockId = stockEntity.getStockId();
        String stockName = stockEntity.getStockName();
        StockCategory category = stockEntity.getCategory();

        // when
        CreateStockResultDto createResult = createStockUseCase.execute(stockId, stockName, category);

        // then
        Assertions.assertThat(createResult)
                .extracting("stockId", "stockName", "category")
                .contains(stockId, stockName, category.name());
    }
}