package nuts.project.wholesale_system.member.domain.service.corporation.usecase.search;

import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchCorporationUseCaseImpl implements SearchCorporationsUseCase {
    @Override
    public List<Corporation> execute(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {
        return null;
    }
}
