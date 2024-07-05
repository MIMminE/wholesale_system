package nuts.project.wholesale_system.member.domain.service.member.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.port.authentication.AuthenticationPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreateMemberUseCaseImpl implements CreateMemberUseCase {

    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final AuthenticationPort authenticationAdapter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member execute(String name, String id, String password, String contactNumber, String corporationId) {

        CorporationEntity corporationEntity = corporationRepository.findById(corporationId).orElseThrow();

        String encryptedPassword = passwordEncoder.encode(password);
        MemberEntity memberEntity = new MemberEntity(name, id, encryptedPassword, contactNumber, corporationEntity);
        memberRepository.save(memberEntity);

        Member member = memberEntity.toMember();
        authenticationAdapter.registerUser(createAuthenticationAdapterRequest(member));
        return memberEntity.toMember();
    }

    private Map<String, Object> createAuthenticationAdapterRequest(Member member) {
        return Map.of("name", member.getName(),
                "contactNumber", member.getContactNumber(),
                "corporation_id", member.getCorporationId());
    }
}
