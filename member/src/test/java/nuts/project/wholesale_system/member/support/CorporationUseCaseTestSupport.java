package nuts.project.wholesale_system.member.support;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.UpdateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationEntity;
import nuts.project.wholesale_system.member.adapter.outbound.repository.corporation.CorporationRepository;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.create.CreateCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete.DeleteCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.get.GetCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.search.SearchCorporationsUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CorporationUseCaseTestSupport {

    @Autowired
    protected CreateCorporationUseCase createCorporationUseCase;

    @Autowired
    protected DeleteCorporationUseCase deleteCorporationUseCase;

    @Autowired
    protected UpdateCorporationUseCase updateCorporationUseCase;


    @Autowired
    protected GetCorporationUseCase getCorporationUseCase;

    @Autowired
    protected SearchCorporationsUseCase searchCorporationsUseCase;

    @Autowired
    protected CorporationRepository corporationRepository;

    protected FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(CorporationEntity.class).setNull("registeredMembers"), 5),
                    OrderSheet.order(Corporation.class, 1),
                    OrderSheet.order(orderCustom(CreateCorporationRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                            .set("contactNumber", Arbitraries.strings()
                                    .withChars("0123456789")
                                    .ofLength(10)
                                    .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                                    .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 1),
                    OrderSheet.order(orderCustom(DeleteCorporationRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                    OrderSheet.order(orderCustom(UpdateCorporationRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                            .set("corporationId", UUID.randomUUID().toString())
                            .set("contactNumber", Arbitraries.strings()
                                    .withChars("0123456789")
                                    .ofLength(10)
                                    .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                                    .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 1))
    );
}
