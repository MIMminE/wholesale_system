package nuts.project.wolesale_system.stock.domain.service.usecase.get;

import nuts.project.wolesale_system.stock.domain.service.dto.GetStockResultDto;
import org.springframework.stereotype.Component;

@Component
public class GetStockUseCaseImpl implements GetStockUseCase{
    @Override
    public GetStockResultDto execute(String stockId) {
        return null;
    }
}
