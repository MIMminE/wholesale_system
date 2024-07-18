package nuts.project.wolesale_system.stock.domain.service.usecase.create;

import nuts.project.wolesale_system.stock.domain.exception.StockException;
import nuts.project.wolesale_system.stock.domain.model.StockCategory;
import nuts.project.wolesale_system.stock.domain.service.dto.CreateStockResultDto;

public interface CreateStockUseCase {

    CreateStockResultDto execute(String stockName, StockCategory category) throws StockException;

}
