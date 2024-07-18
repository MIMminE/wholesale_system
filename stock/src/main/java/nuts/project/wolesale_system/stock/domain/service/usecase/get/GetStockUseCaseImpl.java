package nuts.project.wolesale_system.stock.domain.service.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetStockUseCaseImpl implements GetStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public GetStockResultDto execute(String stockId) {

        StockEntity stockEntity = stockRepository.findById(stockId).orElseThrow(()
                -> new StockException(StockException.StockExceptionCase.NOT_FOUND_ELEMENT));

        return new GetStockResultDto(stockEntity.getStockId(), stockEntity.getStockName(), stockEntity.getCategory().name(), stockEntity.getQuantity());
    }
}
