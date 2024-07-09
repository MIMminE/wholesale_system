package nuts.project.wholesale_system.member.domain.service.corporation.usecase.search;

import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;

import java.util.List;

public interface SearchCorporationsUseCase {
    List<Corporation> execute(String corporationId, String corporationName, String representative, String contactNumber, String businessNumber, Grade grade);
}
