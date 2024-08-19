package nuts.project.wholesale_system.authentication.controller;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authentication-service/login")
    ResponseEntity<TokenResponse> login() {

        TokenResponse tokenResponse = authenticationService.requestToken("testUser", "password");


        // TODO

        return ResponseEntity.ok(tokenResponse);
    }


    /**
     * API to use the user information service registered on the authentication server
     *
     * @return List of User Information
     */
//    @GetMapping("/authentication-service/users")
//    ResponseEntity<AuthUsers> getUsers() {
//
//        authenticationService.getUserTable();
//
//        return ResponseEntity.ok(new AuthUsers(List.of()));
//    }
//
//    @GetMapping("/authentication-service/jwt-sets")
//    ResponseEntity<JWTSetInformation> getJWTSetInformation() {
//        Object jwtSet = authenticationService.getJwkSet();
//        return ResponseEntity.ok((JWTSetInformation) jwtSet);
//    }
}
