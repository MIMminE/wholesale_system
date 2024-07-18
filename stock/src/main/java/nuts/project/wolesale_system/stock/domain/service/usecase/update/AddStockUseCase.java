package nuts.project.wolesale_system.stock.domain.service.usecase.update;

import nuts.project.wolesale_system.stock.domain.service.dto.UpdateStockResultDto;

public interface AddStockUseCase {

    UpdateStockResultDto execute(String stockId, int addQuantity);
}
