package nuts.project.wolesale_system.stock.domain.service.usecase.delete;

import nuts.project.wolesale_system.stock.domain.exception.StockException;

public interface DeleteStockUseCase {

    void execute(String stockId) throws StockException;
}
