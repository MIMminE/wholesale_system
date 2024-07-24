package nuts.project.wholesale_system.member.adapter.inbound.controller.member;


import com.fasterxml.jackson.databind.ObjectMapper;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.DeleteMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.RequestRestDocs;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.UpdateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.response.ResponseRestDocs;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.MemberService;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class MemberControllerTest extends RestDocsSupport {

    private final MemberService memberService = Mockito.mock(MemberService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);

    @Autowired
    private Environment env;

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }


    @DisplayName("[Member Controller 회원 조회 테스트] /api/v1/members/{id}")
    @Test
    void getMember() throws Exception {
        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));

        // given
        String requestId = UUID.randomUUID().toString();
        String corporationId = UUID.randomUUID().toString();

        BDDMockito.given(memberService.getMember(requestId))
                .willReturn(new Member("tester", requestId, "test_password", "010-1234-5678", corporationId));


        // when , then
        mockController.perform(MockMvcRequestBuilders.get("/api/v1/members/%s".formatted(requestId)))
                .andExpect(status().isOk())
                .andExpectAll(MockMvcSupport.mapMatchers((Map.of(
                        "id", requestId,
                        "name", "tester",
                        "contactNumber", "010-1234-5678",
                        "corporationId", corporationId))))
                .andDo(restDocsManager.document("/api/v1/get_members_{id}", "getMemberResponse"));
    }

    @DisplayName("[Member Controller 생성 요청 테스트] /api/v1/members")
    @Test
    void createMember() throws Exception {
        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));
        // given
        String id = UUID.randomUUID().toString();
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String contactNumber = "010-1234-5678";
        String corporationId = UUID.randomUUID().toString();

        CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
                .id(id)
                .name(name)
                .password(password)
                .contactNumber(contactNumber)
                .corporationId(corporationId)
                .build();

        Member returnMember = new Member(name, id, password, contactNumber, corporationId);

        BDDMockito.given(memberService.createMember(name, id, password, contactNumber, corporationId))
                .willReturn(returnMember);

        // when then
        mockController.perform(MockMvcRequestBuilders.post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createMemberRequest)))
                .andExpect(status().isOk())
                .andExpectAll(MockMvcSupport.mapMatchers(Map.of(
                        "id", id,
                        "name", name,
                        "contactNumber", contactNumber,
                        "corporationId", corporationId
                )))
                .andDo(restDocsManager.document("/api/v1/create-members", "createMember", "createMemberResponse"));
    }

    @DisplayName("[Member Controller 삭제 요청 테스트] /api/v1/members")
    @Test
    void deleteMember() throws Exception {
        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));
        // given
        String name = "test_member_name";
        String id = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String contactNumber = "010-1234-5678";
        String corporationId = UUID.randomUUID().toString();

        Member returnMember = new Member(name, id, password, contactNumber, corporationId);

        DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest(id);

        BDDMockito.given(memberService.deleteMember(deleteMemberRequest.getId())).willReturn(returnMember);

        // when then
        mockController.perform(MockMvcRequestBuilders.delete("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(deleteMemberRequest)))
                .andExpect(status().isOk())
                .andExpectAll(MockMvcSupport.mapMatchers(Map.of(
                        "id", id,
                        "name", name,
                        "contactNumber", contactNumber,
                        "corporationId", corporationId
                )))
                .andDo(restDocsManager.document("/api/v1/delete-members", "deleteMember", "deleteMemberResponse"));
    }

    @DisplayName("[Member Controller 수정 요청 테스트] /api/v1/members")
    @Test
    void updateMember() throws Exception {
        Assumptions.assumeTrue(Arrays.asList(env.getActiveProfiles()).contains("unit_test"));

        // given
        String requestId = UUID.randomUUID().toString();
        String name = "test_member_name";
        String password = UUID.randomUUID().toString();
        String contactNumber = "010-1234-5678";
        String corporationId = UUID.randomUUID().toString();

        String newName = "new_test_member_name";
        String newPassword = UUID.randomUUID().toString();
        String newContactNumber = "010-5678-1234";
        String newCorporationId = UUID.randomUUID().toString();

        Member beforeMember = new Member(name, requestId, password, contactNumber, corporationId);
        Member updatedMember = new Member(newName, requestId, newPassword, newContactNumber, newCorporationId);

        UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest(updatedMember.getId(), updatedMember.getName(),
                updatedMember.getPassword(), updatedMember.getContactNumber(), updatedMember.getCorporationId());

        BDDMockito.given(memberService.getMember(requestId)).willReturn(beforeMember);
        BDDMockito.given(memberService.updateMember(requestId, updatedMember)).willReturn(updatedMember);

        // when then
        mockController.perform(MockMvcRequestBuilders.put("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateMemberRequest)))
                .andExpect(status().isOk())
                .andExpectAll(MockMvcSupport.mapMatchers(Map.of(
                        "beforeMember.id", beforeMember.getId(),
                        "beforeMember.name", beforeMember.getName(),
                        "beforeMember.contactNumber", beforeMember.getContactNumber(),
                        "beforeMember.corporationId", beforeMember.getCorporationId(),
                        "updatedMember.id", updatedMember.getId(),
                        "updatedMember.name", updatedMember.getName(),
                        "updatedMember.contactNumber", updatedMember.getContactNumber(),
                        "updatedMember.corporationId", updatedMember.getCorporationId()
                )))
                .andDo(restDocsManager.document("/api/v1/update-members", "updateMember", "updateMemberResponse"))
        ;
    }
}