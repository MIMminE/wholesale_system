package nuts.project.wholesale_system.authentication.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateToken;
import nuts.project.wholesale_system.authentication.controller.request.RequestCreateUsers;
import nuts.project.wholesale_system.authentication.controller.request.RequestDeleteUsers;
import nuts.project.wholesale_system.authentication.controller.response.UserTableResponse;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import nuts.project.wholesale_system.authentication.service.AuthenticationService;
import nuts.project.wholesale_system.authentication.service.dto.UserInformation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authentication-service/token")
    ResponseEntity<TokenResponse> createToken(@RequestBody @Valid RequestCreateToken request) {

        String userName = request.getUserName();
        String password = request.getPassword();
        try {
            TokenResponse tokenResponse = authenticationService.createToken(userName, password);
            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/authentication-service/users")
    ResponseEntity<UserInformation> createUsers(@RequestBody @Valid RequestCreateUsers request) {

        String userName = request.getUserName();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String email = request.getEmail();
        String password = request.getPassword();

        UserInformation userInformation = authenticationService.registerUser(userName, firstName, lastName, email, password);


        return ResponseEntity.ok(userInformation);
    }

    @DeleteMapping("/authentication-service/users")
    ResponseEntity<Boolean> deleteUsers(@RequestBody @Valid RequestDeleteUsers request) {

        String username = request.getUsername();
        boolean result = authenticationService.deleteUser(username);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/authentication-service/users")
    ResponseEntity<UserTableResponse> getUserTable() {
        List<UserInformation> userInformationList = authenticationService.getUserTable();

        return ResponseEntity.ok(new UserTableResponse(userInformationList.size(), userInformationList));
    }

    @GetMapping("/authentication-service/users/{username}")
    ResponseEntity<UserInformation> getUser(@PathVariable("username") String username) {

        return ResponseEntity.ok(authenticationService.getUser(username));
    }

    @GetMapping("/authentication-service/validation/{token}")
    ResponseEntity<Boolean> isValid(@PathVariable("token") String token) throws JOSEException {
        boolean validationResult = authenticationService.validationToken(token);
        return ResponseEntity.ok(validationResult);
    }
}
