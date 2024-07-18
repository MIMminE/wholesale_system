package nuts.project.wolesale_system.stock.domain.service.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockEntity;
import nuts.project.wolesale_system.stock.adapter.outbound.repository.StockRepository;
import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateStockUseCaseImpl implements CreateStockUseCase {

    private final StockRepository stockRepository;

    @Override
    public CreateStockResultDto execute(String stockId, String stockName, StockCategory category) throws StockException {

        StockEntity stockEntity = stockRepository.save(new StockEntity(stockId, stockName, category, 0));

        return new CreateStockResultDto(stockEntity.getStockId(), stockEntity.getStockName(), stockEntity.getCategory().name());
    }
}
