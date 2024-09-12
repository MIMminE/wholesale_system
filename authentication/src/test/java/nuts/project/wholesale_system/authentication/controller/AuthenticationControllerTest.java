package nuts.project.wholesale_system.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.Arbitraries;
import nuts.lib.manager.fixture_manager.FixtureManager;
import nuts.lib.manager.fixture_manager.OrderSheet;
import nuts.lib.manager.restdocs_manager.MockMvcSupport;
import nuts.lib.manager.restdocs_manager.RestDocsManager;
import nuts.lib.manager.restdocs_manager.support.RestDocsSupport;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateToken;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateUsers;
import nuts.project.wholesale_system.authentication.controller.request.RequestDeleteUsers;
import nuts.project.wholesale_system.authentication.controller.request.RequestRestDocs;
import nuts.project.wholesale_system.authentication.controller.response.ResponseRestDocs;
import nuts.project.wholesale_system.authentication.service.AuthenticationService;
import nuts.project.wholesale_system.authentication.service.dto.JwkSet;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@ActiveProfiles("integration-test")
class AuthenticationControllerTest extends RestDocsSupport {

    FixtureManager fixtureManager = new FixtureManager(
            List.of(OrderSheet.order(FixtureManager.orderCustom(UserInformation.class)
                            .set("userId", UUID.randomUUID().toString())
                            .set("userName", UUID.randomUUID().toString())
                            .set("email", UUID.randomUUID().toString()), 3),
                    OrderSheet.order(FixtureManager.orderCustom(JwkSet.class)
                            .size("keys[]", 2)
                            .set("keys[].kid", UUID.randomUUID().toString())
                            .set("keys[].kty", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .set("keys[].alg", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .set("keys[].use", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .set("keys[].n", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .set("keys[].e", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .size("keys[].x5c[]", 2, 4)
                            .set("keys[].x5t", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10))
                            .set("keys[].x5ts256", Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(10)), 1))
    );

    @Mock
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
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(
                Map.of("email", testEmail,
                        "id", userId,
                        "username", testUserName)
        ));

        if (Arrays.asList(environment.getActiveProfiles()).contains("integration-test")) {
            resultActions.andDo(restDocsManager.document("request-create-user", "requestCreateUsers", "responseCreateUsers"));
        }
    }

    @DisplayName("DELETE /authentication-service/users 경로로 요청이 들어오면 서비스 계층의 유저 인증정보 삭제 메서드로 전달하고 결과를 반환한다.")
    @Test
    void deleteUserSuccess() throws Exception {
        // given
        String testUserName = "test-user";
        RequestDeleteUsers requestDeleteUsers = new RequestDeleteUsers(testUserName);
        BDDMockito.given(authenticationService.deleteUser(testUserName)).willReturn(true);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.delete("/authentication-service/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDeleteUsers)));

        // then
        resultActions.andExpect(content().string("true"));
    }

    @DisplayName("GET /authentication-service/users 경로로 요청이 들어오면  서비스 계층의 유저 테이블 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getUserTableSuccess() throws Exception {
        // given
        UserInformation firstUserInformation = fixtureManager.getOrderObjects(UserInformation.class).get(0);
        UserInformation secondUserInformation = fixtureManager.getOrderObjects(UserInformation.class).get(1);

        BDDMockito.given(authenticationService.getUserTable()).willReturn(List.of(firstUserInformation, secondUserInformation));
        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.get("/authentication-service/users"));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(
                Map.of("count", 2,
                        "userInformationList[0].id", firstUserInformation.getUserId(),
                        "userInformationList[0].username", firstUserInformation.getUserName(),
                        "userInformationList[0].email", firstUserInformation.getEmail(),
                        "userInformationList[1].id", secondUserInformation.getUserId(),
                        "userInformationList[1].username", secondUserInformation.getUserName(),
                        "userInformationList[1].email", secondUserInformation.getEmail())
        ));
    }

    @DisplayName("GET /authentication-service/users/{username} 경로로 요청이 들어오면 서비스 계층의 유저 정보 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getUserSuccess() throws Exception {
        // given
        UserInformation userInformation = fixtureManager.getOrderObject(UserInformation.class);
        String requestUserName = userInformation.getUserName();
        BDDMockito.given(authenticationService.getUser(requestUserName)).willReturn(userInformation);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.get("/authentication-service/users/{username}", requestUserName));

        // then
        resultActions.andExpectAll(MockMvcSupport.mapMatchers(Map.of(
                "id", userInformation.getUserId(),
                "username", userInformation.getUserName(),
                "email", userInformation.getEmail()
        )));
    }

    @DisplayName("GET /authentication-service/certs 경로로 요청이 들어오면 서비스 계층의 jwkset 조회 메서드로 전달하고 결과를 반환한다.")
    @Test
    void getJwkSet() throws Exception {
        // given
        JwkSet jwkSet = fixtureManager.getOrderObject(JwkSet.class);
        BDDMockito.given(authenticationService.getJwkSet()).willReturn(jwkSet);

        // when
        ResultActions resultActions = mockController.perform(MockMvcRequestBuilders.get("/authentication-service/certs"));

        // then
        resultActions.andDo(print());
    }

    @Override
    protected Object initController() {
        return new AuthenticationController(authenticationService);
    }
}