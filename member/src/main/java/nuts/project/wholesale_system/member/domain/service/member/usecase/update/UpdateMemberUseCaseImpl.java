package nuts.project.wholesale_system.member.domain.service.member.usecase.update;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase.INVALID_CORPORATION_NUMBER;


@Component
@RequiredArgsConstructor
public class UpdateMemberUseCaseImpl implements UpdateMemberUseCase {

    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member execute(String targetMemberId, Member updateMember) {

        MemberEntity memberEntity = memberRepository.findById(targetMemberId).orElseThrow(
                () -> new MemberUseCaseException(MemberExceptionCase.GET_NO_SUCH_ELEMENT)
        );
        String memberId = memberEntity.getMemberId();

        CorporationEntity corporationEntity = corporationRepository.findById(updateMember.getCorporationId()).orElseThrow(
                () -> new MemberUseCaseException(INVALID_CORPORATION_NUMBER));

        MemberEntity updatedMemberEntity = new MemberEntity(updateMember.getName(), updateMember.getId(), passwordEncoder.encode(updateMember.getPassword()),
                updateMember.getContactNumber(), corporationEntity);

        updatedMemberEntity.setMemberId(memberId);

        memberRepository.delete(memberEntity);
        memberRepository.save(updatedMemberEntity);

        return updatedMemberEntity.toMember();
    }

}
