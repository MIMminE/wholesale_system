package nuts.project.wholesale_system.member.domain.service.corporation;

import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.SearchCorporationsRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.UpdateCorporationRequest;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.create.CreateCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.delete.DeleteCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.get.GetCorporationUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.search.SearchCorporationsUseCase;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CorporationServiceTest extends FixtureGenerateSupport {

    @Mock
    CreateCorporationUseCase createCorporationUseCase;

    @Mock
    DeleteCorporationUseCase deleteCorporationUseCase;

    @Mock
    UpdateCorporationUseCase updateCorporationUseCase;

    @Mock
    SearchCorporationsUseCase searchCorporationsUseCase;

    @Mock
    GetCorporationUseCase getCorporationUseCase;

    @InjectMocks
    CorporationService corporationService;

    @Test
    @DisplayName("[ CorporationService ] 컴포넌트가 [ CreateCorporationUseCase ] 를 호출한다.")
    void createCorporation() {

        // given
        CreateCorporationRequest createCorporationRequest = getOrderedObject(CreateCorporationRequest.class).get(0);
        String corporationId = UUID.randomUUID().toString();
        String corporationName = createCorporationRequest.getCorporationName();
        String representative = createCorporationRequest.getRepresentative();
        String contactNumber = createCorporationRequest.getContactNumber();
        String businessNumber = createCorporationRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(createCorporationRequest.getGrade());

        Corporation corporation = Corporation.builder()
                .corporationId(corporationId)
                .corporationName(corporationName)
                .representative(representative)
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .grade(grade)
                .registeredMembers(new ArrayList<>())
                .build();

        BDDMockito.given(createCorporationUseCase.execute(corporationName, representative, contactNumber, businessNumber, grade)).willReturn(corporation);

        // when
        Corporation resultCorporation = corporationService.createCorporation(corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThat(resultCorporation)
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber", "grade")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade);
    }

    @Test
    @DisplayName("[ CorporationService ] 컴포넌트가 [ DeleteCorporationUseCase ] 를 호출한다.")
    void deleteCorporation() {

        // given
        DeleteCorporationRequest deleteCorporationRequest = getOrderedObject(DeleteCorporationRequest.class).get(0);
        String corporationId = deleteCorporationRequest.getCorporationId();

        Corporation corporation = getOrderedObject(Corporation.class).get(0);
        changeFieldValue(corporation, "corporationId", corporationId);

        String corporationName = corporation.getCorporationName();
        String representative = corporation.getRepresentative();
        String contactNumber = corporation.getContactNumber();
        String businessNumber = corporation.getBusinessNumber();
        Grade grade = corporation.getGrade();
        List<Member> registeredMembers = corporation.getRegisteredMembers();

        BDDMockito.given(deleteCorporationUseCase.execute(corporationId)).willReturn(corporation);

        // when
        Corporation resultCorporation = corporationService.deleteCorporation(corporationId);

        // then
        Assertions.assertThat(resultCorporation)
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber", "grade", "registeredMembers")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade, registeredMembers);
    }

    @Test
    @DisplayName("[ CorporationService ] 컴포넌트가 [ UpdateCorporationUseCase ] 를 호출한다.")
    void updateCorporation() {

        // given
        UpdateCorporationRequest updateCorporationRequest = getOrderedObject(UpdateCorporationRequest.class).get(0);
        Corporation beforeCorporation = getOrderedObject(Corporation.class).get(1);
        changeFieldValue(beforeCorporation, "corporationId", updateCorporationRequest.getCorporationId());

        String corporationId = updateCorporationRequest.getCorporationId();
        String corporationName = updateCorporationRequest.getCorporationName();
        String representative = updateCorporationRequest.getRepresentative();
        String contactNumber = updateCorporationRequest.getContactNumber();
        String businessNumber = updateCorporationRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(updateCorporationRequest.getGrade());

        Corporation updateCorporation = Corporation.builder()
                .corporationId(corporationId)
                .corporationName(corporationName)
                .representative(representative)
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .grade(grade)
                .build();

        BDDMockito.given(updateCorporationUseCase.execute(corporationId, updateCorporation))
                .willReturn(new UpdateCorporationResultSet(beforeCorporation, updateCorporation));

        // when
        UpdateCorporationResultSet resultSet = corporationService.updateCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThat(resultSet)
                .extracting("beforeCorporation", "afterCorporation")
                .contains(beforeCorporation, updateCorporation);
    }


    @Test
    @DisplayName("[ CorporationService ] 컴포넌트가 [ GetCorporationUseCase ] 를 호출한다.")
    void getCorporation() {

        // given
        String corporationId = UUID.randomUUID().toString();

        Corporation corporation = getOrderedObject(Corporation.class).get(2);
        changeFieldValue(corporation, "corporationId", corporationId);

        String corporationName = corporation.getCorporationName();
        String representative = corporation.getRepresentative();
        String contactNumber = corporation.getContactNumber();
        String businessNumber = corporation.getBusinessNumber();
        Grade grade = corporation.getGrade();
        List<Member> registeredMembers = corporation.getRegisteredMembers();

        BDDMockito.given(getCorporationUseCase.execute(corporationId)).willReturn(corporation);

        // when
        Corporation resultCorporation = corporationService.getCorporation(corporationId);

        // then
        Assertions.assertThat(resultCorporation)
                .extracting("corporationId", "corporationName", "representative", "contactNumber", "businessNumber", "grade", "registeredMembers")
                .contains(corporationId, corporationName, representative, contactNumber, businessNumber, grade, registeredMembers);

    }

    @Test
    @DisplayName("[ CorporationService ] 컴포넌트가 [ SearchCorporationUseCase ] 를 호출한다.")
    void searchCorporation() {

        // given
        SearchCorporationsRequest searchCorporationsRequest = getOrderedObject(SearchCorporationsRequest.class).get(0);

        String corporationId = searchCorporationsRequest.getCorporationId();
        String corporationName = searchCorporationsRequest.getCorporationName();
        String representative = searchCorporationsRequest.getRepresentative();
        String contactNumber = searchCorporationsRequest.getContactNumber();
        String businessNumber = searchCorporationsRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(searchCorporationsRequest.getGrade());

        Corporation searchFirstResult = (Corporation) ordersObject().get(0).getArbitraryBuilder().set("grade", Grade.basic).sample();
        Corporation searchSecondResult = (Corporation) ordersObject().get(0).getArbitraryBuilder().set("grade", Grade.basic).sample();

        BDDMockito.given(searchCorporationsUseCase.execute(corporationId, corporationName, representative, contactNumber, businessNumber, grade))
                .willReturn(List.of(searchFirstResult, searchSecondResult));

        // when
        List<Corporation> ressultList = corporationService.searchCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade);

        // then
        Assertions.assertThat(ressultList)
                .containsExactly(searchFirstResult, searchSecondResult);
    }


    @Override
    protected List<OrderSheet> ordersObject() {

        String regex = "010-\\d{4}-\\d{4}";

        return List.of(
                OrderSheet.order(orderCustom(Corporation.class).set("contactNumber", Arbitraries.strings()
                        .withChars("0123456789")
                        .ofLength(10)
                        .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                        .filter(s -> s.matches(regex))), 5),
                OrderSheet.order(orderCustom(CreateCorporationRequest.class)
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(SearchCorporationsRequest.class)
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(orderCustom(UpdateCorporationRequest.class)
                        .set("corporationId", UUID.randomUUID().toString())
                        .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList())), 1),
                OrderSheet.order(DeleteCorporationRequest.class, 1)
        );
    }
}