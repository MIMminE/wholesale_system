package nuts.project.wholesale_system.member.adapter.inbound.controller.corporation;

import nuts.project.wholesale_system.member.domain.exception.CorporationUseCaseException;
import nuts.project.wholesale_system.member.domain.service.corporation.CorporationService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CorporationController.class)
class CorporationExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorporationService service;

    @Test
    void exceptionHandler() throws Exception {
        String corporationId = UUID.randomUUID().toString();

        BDDMockito.given(service.getCorporation(corporationId)).willThrow(new CorporationUseCaseException(CorporationUseCaseException.CorporationExceptionCase.GET_NO_SUCH_ELEMENT));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/corporations/%s".formatted(corporationId)))
                .andExpect(status().isOk());
    }
}