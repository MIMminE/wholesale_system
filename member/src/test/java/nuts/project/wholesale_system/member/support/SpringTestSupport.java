package nuts.project.wholesale_system.member.support;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.UpdateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.member.MemberRepository;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.create.CreateCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete.DeleteCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.get.GetCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.search.SearchCorporationsUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.create.CreateMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.delete.DeleteMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.get.GetMemberUseCase;
import nuts.project.wholesale_system.member.domain.service.member.usecase.update.UpdateMemberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SpringTestSupport extends FixtureGenerateSupport {

    @Autowired
    protected CreateMemberUseCase createMemberUseCase;

    @Autowired
    protected CreateCorporationUseCase createCorporationUseCase;

    @Autowired
    protected DeleteMemberUseCase deleteMemberUseCase;

    @Autowired
    protected DeleteCorporationUseCase deleteCorporationUseCase;

    @Autowired
    protected UpdateMemberUseCase updateMemberUseCase;

    @Autowired
    protected UpdateCorporationUseCase updateCorporationUseCase;

    @Autowired
    protected GetMemberUseCase getMemberUseCase;

    @Autowired
    protected GetCorporationUseCase getCorporationUseCase;

    @Autowired
    protected SearchCorporationsUseCase searchCorporationsUseCase;

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
                OrderSheet.order(orderCustom(CreateCorporationRequest.class)
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(DeleteCorporationRequest.class)
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(UpdateCorporationRequest.class)
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                        .set("corporationId", UUID.randomUUID().toString()), 1),
                OrderSheet.order(Corporation.class, 1),
                OrderSheet.order(MemberEntity.class, 1),
                OrderSheet.order(Member.class, 1));
    }
}
