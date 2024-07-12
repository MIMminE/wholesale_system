package nuts.project.wholesale_system.member.domain.service.corporation.usecase.search;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationDynamicRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchCorporationUseCaseImpl implements SearchCorporationsUseCase {

    private final CorporationDynamicRepository corporationDynamicRepository;

    @Override
    public List<Corporation> execute(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        List<CorporationEntity> corporationEntities = corporationDynamicRepository.search(corporationId, corporationName, representative, contactNumber, businessNumber, grade);

        if (corporationEntities.isEmpty())
            throw new CorporationUseCaseException(CorporationUseCaseException.CorporationExceptionCase.SEARCH_NO_SUCH_ELEMENT);

        return corporationEntities.stream().map(CorporationEntity::toCorporation).toList();
    }
}
