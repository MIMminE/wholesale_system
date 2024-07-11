package nuts.project.wholesale_system.member.domain.service.corporation.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.*;

@Component
@RequiredArgsConstructor
public class UpdateCorporationUseCaseImpl implements UpdateCorporationUseCase {

    private final CorporationRepository corporationRepository;

    @Override
    public UpdateCorporationResultSet execute(String corporationId, Corporation corporation) {
        try {
            CorporationEntity corporationEntity = corporationRepository.findById(corporationId).orElseThrow();
            Corporation beforeCorporation = corporationEntity.toCorporation();

            if (corporation.getCorporationName() != null)
                corporationEntity.setCorporationName(corporation.getCorporationName());
            if (corporation.getRepresentative() != null)
                corporationEntity.setRepresentative(corporation.getRepresentative());
            if (corporation.getContactNumber() != null)
                corporationEntity.setContactNumber(corporation.getContactNumber());
            if (corporation.getBusinessNumber() != null)
                corporationEntity.setBusinessNumber(corporation.getBusinessNumber());
            if (corporation.getGrade() != null) corporationEntity.setGrade(corporation.getGrade());

            Corporation afterCorporation = corporationEntity.toCorporation();
            return new UpdateCorporationResultSet(beforeCorporation, afterCorporation);

        } catch (NoSuchElementException e) {
            throw new CorporationUseCaseException(UPDATE_NO_SUCH_ELEMENT, e);
        }

    }
}
