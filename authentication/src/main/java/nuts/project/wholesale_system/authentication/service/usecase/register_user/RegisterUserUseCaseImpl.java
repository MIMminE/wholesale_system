package nuts.project.wholesale_system.authentication.service.usecase.register_user;

import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.service.cache.UserAuthenticationTableCache;
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
    private final UserAuthenticationTableCache userAuthenticationTableCache;

    public RegisterUserUseCaseImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig, UserAuthenticationTableCache userAuthenticationTableCache) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
        this.authServerSupport = new AuthenticationServerSupport(authServerConfig, restTemplate);
        this.userAuthenticationTableCache = userAuthenticationTableCache;
    }

    @Override
    public UserInformation execute(String userName, String email, String password, String firstName, String lastName) {

        HttpHeaders requestHeader = authServerSupport.getAdminTokenRequestHeader();

        HashMap<String, Object> requestRegister = new HashMap<>();
        requestRegister.put("username", userName);
        requestRegister.put("email", email);
        requestRegister.put("firstName", firstName);
        requestRegister.put("lastName", lastName);
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

        userAuthenticationTableCache.reload();

        return new UserInformation(userId, userName, email);
    }

}
