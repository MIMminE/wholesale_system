package nuts.project.wolesale_system.stock.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import org.springframework.stereotype.Component;

import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.NOT_FOUND_ELEMENT;

@Component
@RequiredArgsConstructor
public class AddStockUseCaseImpl implements AddStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public UpdateStockResultDto execute(String stockId, int addQuantity) {

        StockEntity stockEntity = stockRepository.findById(stockId).orElseThrow(()
                -> new StockException(NOT_FOUND_ELEMENT));

        int beforeQuantity = stockEntity.getQuantity();
        int afterQuantity = beforeQuantity + addQuantity;

        stockEntity.setQuantity(afterQuantity);

        return new UpdateStockResultDto(stockId, stockEntity.getStockName(), stockEntity.getCategory().name(), beforeQuantity, afterQuantity);
    }
}
