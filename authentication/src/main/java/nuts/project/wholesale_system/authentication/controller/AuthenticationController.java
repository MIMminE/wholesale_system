package nuts.project.wholesale_system.authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.request.RequestAuthenticationToken;
import nuts.project.wholesale_system.authentication.controller.response.AuthTokenInformation;
import nuts.project.wholesale_system.authentication.controller.response.AuthUsers;
import nuts.project.wholesale_system.authentication.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;


    /**
     * API for authentication attempts with username and password
     *
     * @param request Login User Information
     * @return About Authenticated JWT Tokens
     */
    @PostMapping("/authentication-service/requestToken")
    ResponseEntity<AuthTokenInformation> requestToken(@RequestBody @Valid RequestAuthenticationToken request) {

        String userName = request.getUserName();
        String password = request.getPassword();

        Map<String, Object> requestTokenResult = authenticationService.requestToken(userName, password);


        // TODO
        String token = requestTokenResult.get("token").toString();


        return ResponseEntity.ok(new AuthTokenInformation(token));
    }


    /**
     * API to use the user information service registered on the authentication server
     *
     * @return List of User Information
     */
    @GetMapping("/authentication-service/users")
    ResponseEntity<AuthUsers> getUsers() {

        Map<String, Object> userTable = authenticationService.getUserTable();

        return ResponseEntity.ok(new AuthUsers(List.of()));
    }
}
