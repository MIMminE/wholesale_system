package nuts.project.wholesale_system.member.domain.service.member.usecase.delete;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase.DELETE_NO_SUCH_ELEMENT;

@Component
@RequiredArgsConstructor
public class DeleteMemberUseCaseImpl implements DeleteMemberUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Member execute(String memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(()
                -> new MemberUseCaseException(DELETE_NO_SUCH_ELEMENT));
        memberRepository.delete(memberEntity);
        return memberEntity.toMember();
    }
}
