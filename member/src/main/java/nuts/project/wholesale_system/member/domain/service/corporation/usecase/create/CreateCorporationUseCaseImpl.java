package nuts.project.wholesale_system.member.domain.service.corporation.usecase.create;

import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.springframework.stereotype.Component;

@Component
public class CreateCorporationUseCaseImpl implements CreateCorporationUseCase {

    @Override
    public Corporation execute(String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {
        return null;
    }
}
