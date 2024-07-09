package nuts.project.wholesale_system.member.domain.service.corporation.usecase.get;

import nuts.project.wholesale_system.member.domain.model.Corporation;

public interface GetCorporationUseCase {

    Corporation execute(String corporationId);
}
