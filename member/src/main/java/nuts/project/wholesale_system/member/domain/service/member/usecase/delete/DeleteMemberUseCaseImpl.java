package nuts.project.wholesale_system.member.domain.service.member.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteMemberUseCaseImpl implements DeleteMemberUseCase{

    private final MemberRepository memberRepository;


    @Override
    public Member execute(String memberId) {

        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow();
        Member member = memberEntity.toMember();
        memberRepository.deleteById(memberId);
        return member;
    }
}
