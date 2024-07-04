package nuts.project.wholesale_system.member.domain.service.member.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateMemberUseCaseImpl implements CreateMemberUseCase {

    private final CorporationRepository corporationRepository;

    @Override
    public Member execute(String name, String contactNumber, String corporationId) {

        Optional<CorporationEntity> corporationEntity = corporationRepository.findById(corporationId);



        return null;
    }


}
