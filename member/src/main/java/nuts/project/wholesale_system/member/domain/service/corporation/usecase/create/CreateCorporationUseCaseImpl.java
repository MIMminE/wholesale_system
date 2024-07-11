package nuts.project.wholesale_system.member.domain.service.corporation.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.*;

@Component
@RequiredArgsConstructor
public class CreateCorporationUseCaseImpl implements CreateCorporationUseCase {

    private final CorporationRepository corporationRepository;

    @Override
    public Corporation execute(String corporationName, String representative, String contactNumber, String businessNumber, Grade grade) {

        CorporationEntity corporationEntity = new CorporationEntity(corporationName, representative, contactNumber, businessNumber, grade);
        CorporationEntity resultEntity = corporationRepository.save(corporationEntity);

        try {
            corporationRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new CorporationUseCaseException(CREATE_REDUNDANCY_BUSINESS_NUMBER, e.fillInStackTrace());
        }

        return resultEntity.toCorporation();
    }
}
