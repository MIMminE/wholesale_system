package nuts.project.wholesale_system.member;

import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.domain.service.member.usecase.create.CreateMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.delete.DeleteMemberUseCase;
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
public class SpringTestSupport extends FixtureGenerateSupport {

    @Autowired
    protected CreateMemberUseCase createMemberUseCase;

    @Autowired
    protected DeleteMemberUseCase deleteMemberUseCase;

    @Autowired
    protected UpdateMemberUseCase updateMemberUseCase;

    @Autowired
    protected CorporationRepository corporationRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    protected List<OrderSheet> ordersObject() {
        return List.of(OrderSheet.order(CorporationEntity.class, 5),
                OrderSheet.order(CreateMemberRequest.class, 1),
                OrderSheet.order(MemberEntity.class, 1),
                OrderSheet.order(Member.class, 1));
    }
}
