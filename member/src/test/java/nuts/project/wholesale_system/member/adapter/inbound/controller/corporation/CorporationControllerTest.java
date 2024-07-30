package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.ExtendsFixtureRestDocsSupport;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.*;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.response.ResponseRestDocs;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.service.corporation.CorporationService;
import nuts.project.wholesale_system.member.domain.service.corporation.usecase.update.UpdateCorporationResultSet;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static java.util.Map.*;
import static nuts.lib.manager.fixture_manager.FixtureManager.changeFieldValue;
import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("unit_test")
class CorporationControllerTest extends RestDocsSupport {

    @Autowired
    private Environment env;

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock

    CorporationService corporationService;

    RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);

    FixtureManager fixtureManager = new FixtureManager(
            List.of(
                    OrderSheet.order(orderCustom(Corporation.class).set("contactNumber", Arbitraries.strings()
                            .withChars("0123456789")
                            .ofLength(10)
                            .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                            .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 2),
                    OrderSheet.order(orderCustom(CreateCorporationRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                            .set("contactNumber", Arbitraries.strings()
                                    .withChars("0123456789")
                                    .ofLength(10)
                                    .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                                    .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 1),
                    OrderSheet.order(orderCustom(SearchCorporationsRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                            .set("contactNumber", Arbitraries.strings()
                                    .withChars("0123456789")
                                    .ofLength(10)
                                    .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                                    .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 1),
                    OrderSheet.order(orderCustom(UpdateCorporationRequest.class)
                            .set("grade", Arbitraries.of(Arrays.stream(Grade.values()).map(Enum::toString).toList()))
                            .set("contactNumber", Arbitraries.strings()
                                    .withChars("0123456789")
                                    .ofLength(10)
                                    .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                                    .filter(s -> s.matches("010-\\d{4}-\\d{4}"))), 1),
                    OrderSheet.order(DeleteCorporationRequest.class, 1)
            )
    );


    @Test
    @DisplayName("GET /member-service/api/v1/corporation/{corporationId} 요청을 받아 [ CorporationService ] 에게 전달한다.")
    void getCorporation() throws Exception {

//        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));

        Corporation corporation = fixtureManager.getOrderObject(Corporation.class);
        String corporationId = corporation.getCorporationId();

        BDDMockito.given(corporationService.getCorporation(corporationId)).willReturn(corporation);

        mockController.perform(MockMvcRequestBuilders.get("/corporation-service/api/v1/corporations/%s".formatted(corporationId)))
                .andExpectAll(MockMvcSupport.mapMatchers(of(
                        "corporationId", corporation.getCorporationId(),
                        "corporationName", corporation.getCorporationName(),
                        "representative", corporation.getRepresentative(),
                        "contactNumber", corporation.getContactNumber(),
                        "businessNumber", corporation.getBusinessNumber(),
                        "grade", corporation.getGrade().toString()
                )))
                .andDo(restDocsManager.document("/api/v1/get-corporations", "getCorporationResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /member-service/api/v1/corporations/search 요청을 받아 [ CorporationService ] 에게 전달한다.")
    void searchCorporation() throws Exception {

//        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));

        SearchCorporationsRequest searchCorporationsRequest = fixtureManager.getOrderObject(SearchCorporationsRequest.class);

        Corporation firstCorporation = fixtureManager.getOrderObjects(Corporation.class).get(0);
        Corporation sceondCorporation = fixtureManager.getOrderObjects(Corporation.class).get(1);

        String corporationId = searchCorporationsRequest.getCorporationId();
        String corporationName = searchCorporationsRequest.getCorporationName();
        String representative = searchCorporationsRequest.getRepresentative();
        String contactNumber = searchCorporationsRequest.getContactNumber();
        String businessNumber = searchCorporationsRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(searchCorporationsRequest.getGrade());

        BDDMockito.given(corporationService.searchCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade))
                .willReturn(List.of(firstCorporation, sceondCorporation));

        mockController.perform(MockMvcRequestBuilders.post("/corporation-service/api/v1/corporations/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCorporationsRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(ofEntries(
                        entry("resultCount", 2),
                        entry("corporationResponses[0].corporationId", firstCorporation.getCorporationId()),
                        entry("corporationResponses[0].corporationName", firstCorporation.getCorporationName()),
                        entry("corporationResponses[0].representative", firstCorporation.getRepresentative()),
                        entry("corporationResponses[0].contactNumber", firstCorporation.getContactNumber()),
                        entry("corporationResponses[0].businessNumber", firstCorporation.getBusinessNumber()),
                        entry("corporationResponses[0].grade", firstCorporation.getGrade().toString()),
                        entry("corporationResponses[1].corporationId", sceondCorporation.getCorporationId()),
                        entry("corporationResponses[1].corporationName", sceondCorporation.getCorporationName()),
                        entry("corporationResponses[1].representative", sceondCorporation.getRepresentative()),
                        entry("corporationResponses[1].contactNumber", sceondCorporation.getContactNumber()),
                        entry("corporationResponses[1].businessNumber", sceondCorporation.getBusinessNumber()),
                        entry("corporationResponses[1].grade", sceondCorporation.getGrade().toString()))
                ))
                .andDo(restDocsManager.document("/api/v1/search-corporations",
                        "searchCorporationRequest",
                        "searchCorporationResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /member-service/api/v1/corporations 요청 정보를 받아 [ CorporationService ] 에게 전달한다.")
    void createCorporation() throws Exception {

//        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));

        CreateCorporationRequest createCorporationRequest = fixtureManager.getOrderObject(CreateCorporationRequest.class);
        System.out.println(createCorporationRequest);
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

        BDDMockito.given(corporationService.createCorporation(corporationName, representative, contactNumber, businessNumber, grade)).willReturn(corporation);


        mockController.perform(MockMvcRequestBuilders.post("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCorporationRequest)))
                .andDo(print())
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("corporationId", corporationId),
                        entry("corporationName", createCorporationRequest.getCorporationName()),
                        entry("representative", createCorporationRequest.getRepresentative()),
                        entry("contactNumber", createCorporationRequest.getContactNumber()),
                        entry("businessNumber", createCorporationRequest.getBusinessNumber()),
                        entry("grade", createCorporationRequest.getGrade())
                )))

                .andDo(restDocsManager.document("/api/v1/create-corporations",
                        "createCorporationRequest",
                        "createCorporationResponse"));
    }

    @Test
    @DisplayName("PUT /member-service/api/v1/corporations 요청 정보를 받아 [ CorporationService ] 에게 전달한다.")
    void updateCorporation() throws Exception {

//        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));
        UpdateCorporationRequest updateCorporationRequest = fixtureManager.getOrderObject(UpdateCorporationRequest.class);

        String corporationId = updateCorporationRequest.getCorporationId();
        String corporationName = updateCorporationRequest.getCorporationName();
        String representative = updateCorporationRequest.getRepresentative();
        String contactNumber = updateCorporationRequest.getContactNumber();
        String businessNumber = updateCorporationRequest.getBusinessNumber();
        Grade grade = Grade.valueOf(updateCorporationRequest.getGrade());

        Corporation beforeCorporation = fixtureManager.getOrderObject(Corporation.class);
        FixtureManager.changeFieldValue(beforeCorporation, "corporationId", corporationId);

        Corporation expectUpdatedCorporation = Corporation.builder()
                .corporationId(corporationId)
                .corporationName(corporationName)
                .representative(representative)
                .contactNumber(contactNumber)
                .businessNumber(businessNumber)
                .grade(grade)
                .registeredMembers(new ArrayList<>())
                .build();

        BDDMockito.given(corporationService.updateCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade))
                .willReturn(new UpdateCorporationResultSet(beforeCorporation, expectUpdatedCorporation));

        mockController.perform(MockMvcRequestBuilders.put("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCorporationRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("beforeCorporation.corporationId", beforeCorporation.getCorporationId()),
                        entry("beforeCorporation.corporationName", beforeCorporation.getCorporationName()),
                        entry("beforeCorporation.representative", beforeCorporation.getRepresentative()),
                        entry("beforeCorporation.contactNumber", beforeCorporation.getContactNumber()),
                        entry("beforeCorporation.businessNumber", beforeCorporation.getBusinessNumber()),
                        entry("beforeCorporation.grade", beforeCorporation.getGrade().toString()),

                        entry("afterCorporation.corporationId", expectUpdatedCorporation.getCorporationId()),
                        entry("afterCorporation.corporationName", expectUpdatedCorporation.getCorporationName()),
                        entry("afterCorporation.representative", expectUpdatedCorporation.getRepresentative()),
                        entry("afterCorporation.contactNumber", expectUpdatedCorporation.getContactNumber()),
                        entry("afterCorporation.businessNumber", expectUpdatedCorporation.getBusinessNumber()),
                        entry("afterCorporation.grade", expectUpdatedCorporation.getGrade().toString())
                )))
                .andDo(restDocsManager.document("/api/v1/update-corporations",
                        "updateCorporationRequest",
                        "updateCorporationResponse"))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /member-service/api/v1/corporations 요청 정보를 받아 [ CorporationService ] 에게 전달한다.")
    void deleteCorporation() throws Exception {

//        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));
        DeleteCorporationRequest deleteCorporationRequest = fixtureManager.getOrderObject(DeleteCorporationRequest.class);

        Corporation corporation = fixtureManager.getOrderObject(Corporation.class);
        changeFieldValue(corporation, "corporationId", deleteCorporationRequest.getCorporationId());

        BDDMockito.given(corporationService.deleteCorporation(deleteCorporationRequest.getCorporationId()))
                .willReturn(corporation);

        mockController.perform(MockMvcRequestBuilders.delete("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCorporationRequest)))
                .andExpectAll(MockMvcSupport.mapMatchers(Map.ofEntries(
                        entry("corporationId", corporation.getCorporationId()),
                        entry("corporationName", corporation.getCorporationName()),
                        entry("representative", corporation.getRepresentative()),
                        entry("businessNumber", corporation.getBusinessNumber())
                )))
                .andDo(restDocsManager.document("/api/v1/delete-corporations",
                        "deleteCorporationRequest",
                        "deleteCorporationResponse"))
                .andDo(print());
    }

    @Override
    protected Object initController() {
        return new CorporationController(corporationService);
    }

}