package nuts.project.wholesale_system.authentication.service;

import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void requestTokenSuccess() {

        // when
        TokenResponse tokenResponse = authenticationService.requestToken("harunut", "123456");

        // then
        Assertions.assertThat(tokenResponse.getTokenType()).isEqualTo("Bearer");
        Assertions.assertThat(tokenResponse.getAccessToken()).isNotBlank();
    }

    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void registerUserSuccess() {

        // when
        UserInformation haasdrunut = authenticationService.registerUser("haasdrunut", "harun1uts@naver.com", "123456");
        System.out.println(haasdrunut);

        // then
    }

    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void getUserTableSuccess() {

        List<UserInformation> userTable = authenticationService.getUserTable();

        // when
        for (UserInformation userInformation : userTable) {
            System.out.println(userInformation);
        }


        // then
    }

    //
    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void getUserSuccess() {

        UserInformation harunut = authenticationService.getUser("haasdrunut");
        System.out.println(harunut);

        // when


        // then
    }

    @DisplayName("유저 이름과 패스워드를 인증 서버로 보내 토큰을 발급받고 반환한다.")
    @Test
    void deleteUserSuccess() {

        boolean haasdrunut = authenticationService.deleteUser("haasdrunut");
        System.out.println(haasdrunut);


        // when


        // then
    }


}