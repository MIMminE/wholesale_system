package nuts.project.wolesale_system.stock.domain.service.usecase.delete;

import nuts.project.wolesale_system.stock.domain.exception.StockException;
import org.springframework.stereotype.Component;

@Component
public class DeleteStockUseCaseImpl implements DeleteStockUseCase{
    @Override
    public void execute(String stockId) throws StockException {

    }
}
