package nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCorporationUseCaseImpl implements DeleteCorporationUseCase {

    private final CorporationRepository corporationRepository;

    @Override
    public Corporation execute(String corporationId) {
        CorporationEntity corporationEntity = corporationRepository.findById(corporationId).orElseThrow();
        corporationRepository.delete(corporationEntity);
        return corporationEntity.toCorporation();
    }
}
