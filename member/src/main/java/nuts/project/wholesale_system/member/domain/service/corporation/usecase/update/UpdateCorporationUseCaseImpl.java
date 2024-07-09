package nuts.project.wholesale_system.member.domain.service.corporation.usecase.update;

import nuts.project.wholesale_system.member.domain.model.Corporation;
import org.springframework.stereotype.Component;

@Component
public class UpdateCorporationUseCaseImpl implements UpdateCorporationUseCase{
    @Override
    public UpdateCorporationResultSet execute(String corporationId, Corporation corporation) {
        return null;
    }
}
