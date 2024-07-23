package nuts.project.wholesale_system.member.domain.service.member.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase.*;

@Component
@RequiredArgsConstructor
public class GetMemberUseCaseImpl implements GetMemberUseCase {

    private final MemberRepository memberRepository;

    @Override
    public Member execute(String id) {

        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()
                -> new MemberUseCaseException(GET_NO_SUCH_ELEMENT));

        return memberEntity.toMember();
    }

}
