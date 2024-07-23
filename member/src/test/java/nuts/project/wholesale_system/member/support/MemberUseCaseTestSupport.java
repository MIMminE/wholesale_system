package nuts.project.wholesale_system.member.support;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.usecase.create.CreateMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.delete.DeleteMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.get.GetMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.update.UpdateMemberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberUseCaseTestSupport extends FixtureGenerateSupport {

    @Autowired
    protected CreateMemberUseCase createMemberUseCase;

    @Autowired
    protected DeleteMemberUseCase deleteMemberUseCase;

    @Autowired
    protected UpdateMemberUseCase updateMemberUseCase;

    @Autowired
    protected GetMemberUseCase getMemberUseCase;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected CorporationRepository corporationRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(
                OrderSheet.order(orderCustom(CorporationEntity.class).size("registeredMembers", 2)
                        .setNull("registeredMembers[*].corporationEntity"), 1),
                OrderSheet.order(CreateMemberRequest.class, 1),
                OrderSheet.order(orderCustom(MemberEntity.class).size("corporationEntity.registeredMembers", 2)
                        .setNull("corporationEntity.registeredMembers[*].corporationEntity"), 1),
                OrderSheet.order(Member.class, 1));
    }
}
