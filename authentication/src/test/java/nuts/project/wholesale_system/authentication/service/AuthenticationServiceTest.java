package nuts.project.wholesale_system.authentication.service;

import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @DisplayName("")
    @Test
    void test() {
        // given
        TokenResponse tokenResponse = authenticationService.requestToken(",", "");
//        System.out.println(tokenResponse);

        // when

        Map<String, Object> userTable = authenticationService.getUserTable();
        System.out.println(userTable);

        // then
    }

}