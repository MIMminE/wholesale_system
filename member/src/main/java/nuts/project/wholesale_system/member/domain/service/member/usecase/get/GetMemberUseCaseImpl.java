package nuts.project.wholesale_system.member.domain.service.member.usecase.get;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetMemberUseCaseImpl implements GetMemberUseCase{

    private final PasswordEncoder passwordEncoder;
    @Override
    public Member execute(String id) {
        return null;
    }

    private final MemberRepository memberRepository;
}
