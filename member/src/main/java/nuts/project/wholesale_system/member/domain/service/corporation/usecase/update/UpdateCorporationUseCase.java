package nuts.project.wholesale_system.member.domain.service.corporation.usecase.update;

import nuts.project.wholesale_system.member.domain.model.Corporation;

public interface UpdateCorporationUseCase {
    UpdateCorporationResultSet execute(String corporationId, Corporation corporation);

}
