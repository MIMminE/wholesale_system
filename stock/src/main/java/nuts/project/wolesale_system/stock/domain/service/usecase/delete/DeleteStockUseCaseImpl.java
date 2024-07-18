package nuts.project.wolesale_system.stock.domain.service.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteStockUseCaseImpl implements DeleteStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public void execute(String stockId) throws StockException {

        StockEntity stockEntity = stockRepository.findById(stockId).orElseThrow(()
                -> new StockException(StockException.StockExceptionCase.NOT_FOUND_ELEMENT));

        stockRepository.delete(stockEntity);
    }
}
