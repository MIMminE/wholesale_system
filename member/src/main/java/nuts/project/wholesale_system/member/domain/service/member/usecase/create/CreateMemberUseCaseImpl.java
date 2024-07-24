package nuts.project.wholesale_system.member.domain.service.member.usecase.create;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.port.authentication.AuthenticationPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase.GET_NO_SUCH_ELEMENT;


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
        MemberEntity savedEntity = memberRepository.save(
                new MemberEntity(name, id, encryptedPassword, contactNumber, corporationEntity));

        try {
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new MemberUseCaseException(GET_NO_SUCH_ELEMENT, e);
        }

        Member member = savedEntity.toMember();
        authenticationAdapter.registerUser(createAuthenticationAdapterRequest(member));
        return member;
    }

    private Map<String, Object> createAuthenticationAdapterRequest(Member member) {
        return Map.of("name", member.getName(),
                "contactNumber", member.getContactNumber(),
                "corporationId", member.getCorporationId());
    }
}
