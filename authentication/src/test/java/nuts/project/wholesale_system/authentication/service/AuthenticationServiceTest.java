package nuts.project.wholesale_system.authentication.service;

import com.nimbusds.jose.jwk.JWKSet;
import nuts.project.wholesale_system.authentication.service.dto.JwkSet;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import nuts.project.wholesale_system.authentication.exception.AuthenticationException;
import nuts.project.wholesale_system.authentication.service.cache.UserAuthenticationTableCache;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserAuthenticationTableCache userAuthenticationTableCache;

    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void requestTokenSuccess() {

        // given
        String testUserName = "testusername";
        String testEmail = "testemail@naver.com";
        String testPassword = "testpassword";
        String firstName = "test_firstname";
        String lastName = "test_lastname";
        testUserSetUp(testUserName, testEmail, testPassword, firstName, lastName);

        try {
            // when
            TokenResponse tokenResponse = authenticationService.createToken(testUserName, testPassword);
            // then
            Assertions.assertThat(tokenResponse.getTokenType()).isEqualTo("Bearer");
            Assertions.assertThat(tokenResponse.getAccessToken()).isNotBlank();

        } finally {
            testUserTearDown(testUserName);
        }
    }


    @DisplayName("인증 서버에 유저 인증 정보 등록 요청을 보내고 결과를 반환한다.")
    @Test
    void registerUserSuccess() {

        // given
        String testUserName = "testusername";
        String testEmail = "testemail@naver.com";
        String testPassword = "testpassword";
        String firstName = "test_firstname";
        String lastName = "test_lastname";

        // when
        authenticationService.registerUser(testUserName, firstName, lastName, testEmail, testPassword);
        UserInformation user = authenticationService.getUser(testUserName);

        // then
        Assertions.assertThat(user)
                .extracting("userName", "email")
                .contains(testUserName, testEmail);

        testUserTearDown(testUserName);
    }

    @DisplayName("인증 서버에 유저 인증 정보 삭제 요청을 보내고 결과를 반환한다.")
    @Test
    void deleteUserSuccess() {

        // given
        String testUserName = "testusername";
        String testEmail = "testemail@naver.com";
        String testPassword = "testpassword";
        String firstName = "test_firstname";
        String lastName = "test_lastname";
        testUserSetUp(testUserName, testEmail, testPassword, firstName, lastName);

        // when
        boolean result = authenticationService.deleteUser(testUserName);

        // then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThatThrownBy(() -> authenticationService.getUser(testUserName))
                .hasMessage(AuthenticationException.AuthenticationExceptionType.USER_NOT_FOUND.name());

    }

    @DisplayName("인증 서버에 유저 테이블 조회 요청을 보내고 결과를 반환한다.")
    @Test
    void getUserTableSuccess() {

        // given
        String testUserName = "testusername";
        String testEmail = "testemail@naver.com";
        String testPassword = "testpassword";
        String firstName = "test_firstname";
        String lastName = "test_lastname";

        String testUserName2 = "testusername2";
        String testEmail2 = "testemail2@naver.com";
        String testPassword2 = "testpassword";
        String firstName2 = "test_firstname2";
        String lastName2 = "test_lastname2";


        try {

            testUserSetUp(testUserName, testEmail, testPassword, firstName, lastName);
            testUserSetUp(testUserName2, testEmail2, testPassword2, firstName2, lastName2);

            // when
            List<UserInformation> userTable = authenticationService.getUserTable();

            // then
            UserInformation firstUserInformation = userTable.get(0);
            UserInformation secondUserInformation = userTable.get(1);

            Assertions.assertThat(firstUserInformation)
                    .extracting("userName", "email")
                    .contains(testUserName, testEmail);


            Assertions.assertThat(secondUserInformation)
                    .extracting("userName", "email")
                    .contains(testUserName2, testEmail2);
        } finally {
            testUserTearDown(testUserName);
            testUserTearDown(testUserName2);
        }
    }

    //
    @DisplayName("요청한 유저 이름에 해당하는 인증 정보를 반환한다.")
    @Test
    void getUserSuccess() {

        // given
        String testUserName = "testusername";
        String testEmail = "testemail@naver.com";
        String testPassword = "testpassword";
        String firstName = "test_firstname";
        String lastName = "test_lastname";

        try {
            testUserSetUp(testUserName, testEmail, testPassword, firstName, lastName);

            // when
            UserInformation user = authenticationService.getUser(testUserName);

            // then
            Assertions.assertThat(user)
                    .extracting("userName", "email")
                    .contains(testUserName, testEmail);

        } finally {
            testUserTearDown(testUserName);
        }
    }

    @DisplayName("인증 서버에 JwkSet 조회 요청을 보내고 결과를 반환한다.")
    @Test
    void getJwkSet() {

        // when
        JWKSet jwkSet = authenticationService.getJwkSet();

        // then
        Assertions.assertThat(jwkSet).isNotNull();
    }

    private void testUserSetUp(String testUserName, String testEmail, String testPassword, String firstName, String lastName) {
        authenticationService.registerUser(testUserName, firstName, lastName, testEmail, testPassword);
    }

    private void testUserTearDown(String testUserName) {
        authenticationService.deleteUser(testUserName);
    }
}