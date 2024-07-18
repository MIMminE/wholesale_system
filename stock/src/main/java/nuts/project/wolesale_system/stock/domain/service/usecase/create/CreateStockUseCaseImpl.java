package nuts.project.wolesale_system.stock.domain.service.usecase.create;

import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;
import org.springframework.stereotype.Component;

@Component
public class CreateStockUseCaseImpl implements CreateStockUseCase{

    @Override
    public CreateStockResultDto execute(String stockName, StockCategory category) throws StockException {
        return null;
    }
}
