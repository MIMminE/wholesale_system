package nuts.project.wholesale_system.member.domain.service.corporation.usecase.create;

import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;

public interface CreateCorporationUseCase {
    Corporation execute(String corporationName, String representative, String contactNumber, String businessNumber, Grade grade);
}
