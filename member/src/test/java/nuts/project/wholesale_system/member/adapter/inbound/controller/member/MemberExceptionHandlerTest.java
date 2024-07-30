package nuts.project.wholesale_system.member.adapter.inbound.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.CreateMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.DeleteMemberRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.member.dto.request.UpdateMemberRequest;
import nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Member;
import nuts.project.wholesale_system.member.domain.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static nuts.lib.manager.fixture_manager.FixtureManager.orderCustom;
import static nuts.project.wholesale_system.member.domain.exception.MemberUseCaseException.MemberExceptionCase.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@DisplayName("공통 예외 핸들러 (Member)")
@ActiveProfiles("test")
class MemberExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService service;

    ObjectMapper mapper = new ObjectMapper();

    FixtureManager fixtureManager = new FixtureManager(List.of(
            OrderSheet.order(CreateMemberRequest.class, 1),
            OrderSheet.order(DeleteMemberRequest.class, 1),
            OrderSheet.order(orderCustom(UpdateMemberRequest.class)
                    .minSize("requestId", 2), 1)
    ));

    @Test
    @DisplayName("회원 생성 요청에서 이미 등록된 ID로 요청한 경우 발생하는 예외의 핸들링에 성공한다.")
    void createMemberExceptionHandle() throws Exception {
        CreateMemberRequest createMemberRequest = fixtureManager.getOrderObject(CreateMemberRequest.class);

        String name = createMemberRequest.getName();
        String memberId = createMemberRequest.getId();
        String contactNumber = createMemberRequest.getContactNumber();
        String password = createMemberRequest.getPassword();
        String corporationId = createMemberRequest.getCorporationId();

        BDDMockito.given(service.createMember(name, memberId, password, contactNumber, corporationId))
                .willThrow(new MemberUseCaseException(ALREADY_IN_USE_ID));

        mockMvc.perform(MockMvcRequestBuilders.post("/member-service/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMemberRequest)))
                .andExpect(content().string(ALREADY_IN_USE_ID.getMessage()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 삭제 요청에서 삭제하고자하는 회원 정보가 존재하지 않을 때 발생하는 예외의 핸들링에 성공한다.")
    void deleteMemberExceptionHandle() throws Exception {
        DeleteMemberRequest deleteMemberRequest = fixtureManager.getOrderObject(DeleteMemberRequest.class);

        String id = deleteMemberRequest.getId();

        BDDMockito.given(service.deleteMember(id))
                .willThrow(new MemberUseCaseException(DELETE_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.delete("/member-service/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteMemberRequest)))
                .andExpect(content().string(DELETE_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 조회 요청에서 존재하지 않는 회원에 대한 요청일 경우 발생하는 예외의 핸들링에 성공한다.")
    void getMemberExceptionHandle() throws Exception {
        String memberId = UUID.randomUUID().toString();

        BDDMockito.given(service.getMember(memberId))
                .willThrow(new MemberUseCaseException(GET_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.get("/member-service/api/v1/members/%s".formatted(memberId)))
                .andExpect(content().string(GET_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 업데이트 요청에서 존재하지 않는 회원 ID에 대한 업데이트를 요청할 경우 발생하는 예외의 핸들링에 성공한다.")
    void updateMemberExceptionHandle() throws Exception {
        UpdateMemberRequest updateMemberRequest = fixtureManager.getOrderObject(UpdateMemberRequest.class);

        String requestId = updateMemberRequest.getRequestId();
        String newName = updateMemberRequest.getNewName();
        String newContactNumber = updateMemberRequest.getNewContactNumber();
        String newPassword = updateMemberRequest.getNewPassword();
        String newCorporationId = updateMemberRequest.getNewCorporationId();

        Member member = new Member(newName, requestId, newPassword, newContactNumber, newCorporationId);

        BDDMockito.given(service.updateMember(requestId, member))
                .willThrow(new MemberUseCaseException(INVALID_CORPORATION_NUMBER));

        mockMvc.perform(MockMvcRequestBuilders.put("/member-service/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateMemberRequest)))
                .andExpect(status().isBadRequest());
    }
//
//    @Test
//    @DisplayName("인증 서비스와의 통신에 실패한 경우 발생하는 예외의 핸들링에 성공한다.")
//    void authenticationException() throws Exception {
//        String corporationId = UUID.randomUUID().toString();
//
//        BDDMockito.given(service.getCorporation(corporationId))
//                .willThrow(new CorporationUseCaseException(CorporationUseCaseException.CorporationExceptionCase.GET_NO_SUCH_ELEMENT));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/corporation-service/api/v1/corporations/%s".formatted(corporationId)))
//                .andExpect(status().isOk());
//    }

}