package nuts.project.wolesale_system.stock.domain.service.usecase.get;

import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;

public interface GetStockUseCase {

    GetStockResultDto execute(String stockId);
}
