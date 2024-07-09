package nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete;

import nuts.project.wholesale_system.member.domain.model.Corporation;

public interface DeleteCorporationUseCase {
    Corporation execute(String corporationId);
}
