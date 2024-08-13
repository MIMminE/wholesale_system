package nuts.project.wholesale_system.authentication.service.usecase.register_user;

import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.service.usecase.AuthenticationServerSupport;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;
    private final AuthenticationServerSupport authServerSupport;

    public RegisterUserUseCaseImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
        this.authServerSupport = new AuthenticationServerSupport(authServerConfig, restTemplate);
    }

    @Override
    public UserInformation execute(String username, String email, String password) {

        HttpHeaders requestHeader = authServerSupport.getAdminTokenRequestHeader();

        HashMap<String, Object> requestRegister = new HashMap<>();
        requestRegister.put("username", username);
        requestRegister.put("email", email);
        requestRegister.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("value", password);
        requestRegister.put("credentials", List.of(credentials));

        HttpEntity<Object> requestUserInfo = new HttpEntity<>(requestRegister, requestHeader);
        String requestUrl = String.format("%s/admin/realms/%s/users", authServerConfig.getUrl(), authServerConfig.getRealms());

        ResponseEntity<Void> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestUserInfo, Void.class);

        HttpHeaders headers = response.getHeaders();
        String[] parts = headers.get("Location").get(0).split("/");
        String userId = parts[parts.length - 1];

        return new UserInformation(userId, username, email);
    }
}
