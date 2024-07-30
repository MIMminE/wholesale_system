package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureGenerateSupport;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.CreateCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.DeleteCorporationRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.SearchCorporationsRequest;
import nuts.project.wholesale_system.member.adapter.inbound.controller.corporation.dto.request.UpdateCorporationRequest;
import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.model.Corporation;
import nuts.project.wholesale_system.member.domain.model.Grade;
import nuts.project.wholesale_system.member.domain.service.corporation.CorporationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.*;
import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.CREATE_REDUNDANCY_BUSINESS_NUMBER;
import static nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException.CorporationExceptionCase.DELETE_NO_SUCH_ELEMENT;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CorporationController.class)
@DisplayName("공통 예외 핸들러 (Corporation)")
class CorporationExceptionHandlerTest extends FixtureGenerateSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorporationService service;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("기관 생성 요청에서 동일한 기관 번호로 요청할 경우 발생하는 예외의 핸들링에 성공한다.")
    void createCorporationExceptionHandle() throws Exception {
        Corporation corporation = getOrderedObject(Corporation.class).get(0);

        String corporationName = corporation.getCorporationName();
        String representative = corporation.getRepresentative();
        String contactNumber = corporation.getContactNumber();
        String businessNumber = corporation.getBusinessNumber();
        Grade grade = corporation.getGrade();

        CreateCorporationRequest createCorporationRequest = new CreateCorporationRequest(corporationName, representative, contactNumber, businessNumber, grade.toString());

        BDDMockito.given(service.createCorporation(corporationName, representative, contactNumber, businessNumber, grade))
                .willThrow(new CorporationUseCaseException(CREATE_REDUNDANCY_BUSINESS_NUMBER));

        mockMvc.perform(MockMvcRequestBuilders.post("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCorporationRequest)))
                .andExpect(jsonPath("$.message").value(CREATE_REDUNDANCY_BUSINESS_NUMBER.getMessage()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("기관 삭제 요청에서 존재하지 않는 기관에 대한 요청일 경우 발생하는 예외의 핸들링에 성공한다.")
    void deleteCorporationExceptionHandle() throws Exception {

        String corporationId = UUID.randomUUID().toString();

        DeleteCorporationRequest deleteCorporationRequest = new DeleteCorporationRequest(corporationId);

        BDDMockito.given(service.deleteCorporation(corporationId))
                .willThrow(new CorporationUseCaseException(DELETE_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.delete("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteCorporationRequest))
                )
                .andExpect(jsonPath("$.message").value(DELETE_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("기관 조회 요청에서 존재하지 않는 기관 ID로 요청할 경우 발생하는 예외의 핸들링에 성공한다.")
    void getCorporationExceptionHandle() throws Exception {
        String corporationId = UUID.randomUUID().toString();

        BDDMockito.given(service.getCorporation(corporationId))
                .willThrow(new CorporationUseCaseException(GET_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.get("/corporation-service/api/v1/corporations/%s".formatted(corporationId)))
                .andExpect(jsonPath("$.message").value(GET_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("기관 검색 조회 요청에서 검색 조건에 맞는 데이터가 없을 때 발생하는 예외의 핸들링에 성공한다.")
    void searchCorporationExceptionHandle() throws Exception {

        String corporationId = UUID.randomUUID().toString();
        String corporationName = UUID.randomUUID().toString();
        String contactNumber = "010-5123-1234";

        SearchCorporationsRequest searchCorporationsRequest = new SearchCorporationsRequest(corporationId, corporationName,
                null, contactNumber, null, Grade.basic.name());

        BDDMockito.given(service.searchCorporation(corporationId, corporationName, null, contactNumber, null, Grade.basic))
                .willThrow(new CorporationUseCaseException(SEARCH_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.post("/corporation-service/api/v1/corporations/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchCorporationsRequest)))
                .andExpect(jsonPath("$.message").value(SEARCH_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("기관 업데이트 요청에서 존재하지 않는 기관 ID에 대한 업데이트를 요청할 경우 발생하는 예외의 핸들링에 성공한다.")
    void updateCorporationExceptionHandle() throws Exception {
        Corporation corporation = getOrderedObject(Corporation.class).get(0);

        String corporationId = corporation.getCorporationId();
        String corporationName = corporation.getCorporationName();
        String representative = corporation.getRepresentative();
        String contactNumber = corporation.getContactNumber();
        String businessNumber = corporation.getBusinessNumber();
        Grade grade = corporation.getGrade();

        UpdateCorporationRequest updateCorporationRequest = new UpdateCorporationRequest(corporationId, corporationName, representative, contactNumber, businessNumber, grade.name());

        BDDMockito.given(service.updateCorporation(corporationId, corporationName, representative, contactNumber, businessNumber, grade))
                .willThrow(new CorporationUseCaseException(UPDATE_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.put("/corporation-service/api/v1/corporations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateCorporationRequest)))
                .andExpect(jsonPath("$.message").value(UPDATE_NO_SUCH_ELEMENT.getMessage()))
                .andExpect(status().isOk());
    }

    @Override
    protected List<OrderSheet> ordersObject() {

        String regex = "010-\\d{4}-\\d{4}";

        return List.of(
                OrderSheet.order(orderCustom(Corporation.class).set("contactNumber", Arbitraries.strings()
                        .withChars("0123456789")
                        .ofLength(10)
                        .map(s -> "010-" + s.substring(0, 4) + "-" + s.substring(4, 8))
                        .filter(s -> s.matches(regex))), 2)
        );
    }
}