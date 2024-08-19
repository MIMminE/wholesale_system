package nuts.project.wholesale_system.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateToken;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateUsers;
import nuts.project.wholesale_system.authentication.controller.request.RequestRestDocs;
import nuts.project.wholesale_system.authentication.controller.response.ResponseRestDocs;
import nuts.project.wholesale_system.authentication.service.AuthenticationService;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@ActiveProfiles("integration-test")
class AuthenticationControllerTest extends RestDocsSupport {

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    Environment environment;

    private final ObjectMapper mapper = new ObjectMapper();

    private final RestDocsManager restDocsManager = new RestDocsManager(RequestRestDocs.class, ResponseRestDocs.class);

    @DisplayName("POST /authentication-service/token 경로로 요청이 들어오면 서비스 계층의 토큰 발급 메서드로 전달하고 결과를 반환한다.")
    @Test
    void requestTokenSuccess() throws Exception {

        // given
        String testUser = "test-user";
        String testPassword = "test-password";

        RequestCreateToken requestCreateToken = new RequestCreateToken(testUser, testPassword);

        String sampleAccessToken = UUID.randomUUID().toString();

        TokenResponse tokenResponse = new TokenResponse(sampleAccessToken, 10, 10, "Bearer", 0, "email");

        BDDMockito.given(authenticationService.createToken(testUser, testPassword))
                .willReturn(tokenResponse);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.post("/authentication-service/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestCreateToken))
        );

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(
                Map.of("scope", "email",
                        "access_token", sampleAccessToken)
        ));

        if (Arrays.asList(environment.getActiveProfiles()).contains("integration-test")) {
            resultActions.andDo(restDocsManager.document("test", "requestCreateToken", "tokenResponse"));
        }
    }

    @DisplayName("POST /authentication-service/users 경로로 요청이 들어오면 서비스 계층의 유저 인증정보 등록 메서드로 전달하고 결과를 반환한다.")
    @Test
    void registerUserSuccess() throws Exception {
        // given
        String testUserName = "test-user";
        String testFirstName = "test-first-name";
        String testLastName = "test-last-name";
        String testEmail = "test-email@naver.com";
        String testPassword = "test-password";

        RequestCreateUsers requestCreateUsers = new RequestCreateUsers(testUserName, testFirstName, testLastName, testEmail, testPassword);

        String userId = UUID.randomUUID().toString();

        UserInformation userInformation = new UserInformation(userId, testUserName, testEmail);

        BDDMockito.given(authenticationService.registerUser(testUserName, testFirstName, testLastName, testEmail, testPassword))
                .willReturn(userInformation);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.post("/authentication-service/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestCreateUsers)));

        // then
        resultActions.andDo(print());
    }

    @DisplayName("DELETE /authentication-service/users 경로로 요청이 들어오면 서비스 계층의 유저 인증정보 삭제 메서드로 전달하고 결과를 반환한다.")
    @Test
    void deleteUserSuccess() {
        // given

        // when

        // then
    }

    @DisplayName("GET /authentication-service/users 경로로 요청이 들어오면  서비스 계층의 유저 테이블 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getUserTableSuccess() {
        // given

        // when

        // then
    }

    @DisplayName("GET /authentication-service/users/{username} 경로로 요청이 들어오면 서비스 계층의 유저 정보 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getUserSuccess() {
        // given

        // when

        // then
    }

    @DisplayName("GET /authentication-service/certs 경로로 여청이 들어오면 서비스 계층의 jwkset 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getJwkSet() {
        // given

        // when

        // then
    }

    @Override
    protected Object initController() {
        return new AuthenticationController(authenticationService);
    }
}