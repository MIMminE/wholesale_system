package nuts.project.wolesale_system.stock.domain.service.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;
import org.springframework.stereotype.Component;

import static nuts.project.wolesale_system.stock.domain.exception.StockException.StockExceptionCase.*;

@Component
@RequiredArgsConstructor
public class DeductStockUseCaseImpl implements DeductStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public UpdateStockResultDto execute(String stockId, int deductQuantity) {

        StockEntity stockEntity = stockRepository.findById(stockId).orElseThrow(()
                -> new StockException(NOT_FOUND_ELEMENT));

        int beforeQuantity = stockEntity.getQuantity();
        if (deductQuantity > beforeQuantity) throw new StockException(OUT_OF_STOCK);
        int afterQuantity = beforeQuantity - deductQuantity;

        stockEntity.setQuantity(afterQuantity);

        return new UpdateStockResultDto(stockId, stockEntity.getStockName(), stockEntity.getCategory().name(), beforeQuantity, afterQuantity);
    }
}
