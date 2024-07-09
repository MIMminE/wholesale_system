package nuts.project.wholesale_system.member.domain.service.corporation;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.create.CreateCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete.DeleteCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.get.GetCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.search.SearchCorporationsUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorporationService {

    private final CreateCorporationUseCase createCorporationUseCase;
    private final DeleteCorporationUseCase deleteCorporationUseCase;
    private final UpdateCorporationUseCase updateCorporationUseCase;
    private final SearchCorporationsUseCase searchCorporationsUseCase;
    private final GetCorporationUseCase getCorporationUseCase;

    public Corporation createCorporation(String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        return createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade);
    }

    public Corporation deleteCorporation(String corporationId) {


        return deleteCorporationUseCase.execute(corporationId);
    }

    public UpdateCorporationResultSet updateCorporation(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        Corporation requestCorporation = Corporation.builder()
                .corporationId(corporationId)
                .corporationName(corporationName)
                .representative(representative)
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .grade(grade)
                .build();

        return updateCorporationUseCase.execute(corporationId, requestCorporation);
    }

    public Corporation getCorporation(String corporationId) {
        return getCorporationUseCase.execute(corporationId);
    }

    public List<Corporation> searchCorporation(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        return searchCorporationsUseCase.execute(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
    }

}
