package nuts.project.wholesale_system.authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.request.RequestAuthenticationToken;
import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.controller.response.AuthUsers;
import nuts.project.wholesale_system.authentication.controller.response.JWTSetInformation;
import nuts.project.wholesale_system.authentication.service.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

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
    @GetMapping("/authentication-service/users")
    ResponseEntity<AuthUsers> getUsers() {

        authenticationService.getUserTable();

        return ResponseEntity.ok(new AuthUsers(List.of()));
    }

    @GetMapping("/authentication-service/jwt-sets")
    ResponseEntity<JWTSetInformation> getJWTSetInformation() {
        Object jwtSet = authenticationService.getJwkSet();
        return ResponseEntity.ok((JWTSetInformation) jwtSet);
    }
}
