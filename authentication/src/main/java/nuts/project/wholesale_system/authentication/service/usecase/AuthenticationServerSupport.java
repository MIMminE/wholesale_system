package nuts.project.wholesale_system.authentication.service.usecase;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthenticationServerSupport {

    private final AuthServerProperties authServerConfig;
    private final RestTemplate restTemplate;

    public HttpHeaders getAdminTokenRequestHeader() {

        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "client_credentials");
        requestToken.add("client_id", "admin-cli");
        requestToken.add("client_secret", "y6AOV8SqW7LqZ8e0jdkeXEu7eJq92VDy");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestToken, headers);

        String authServerUrl = authServerConfig.getUrl();


        String adminAccessToken = Objects.requireNonNull(restTemplate.postForObject(authServerUrl + "/realms/auth-service/protocol/openid-connect/token", request, TokenResponse.class))
                .getAccessToken();

        HttpHeaders requestUserInfoHeader = new HttpHeaders();
        requestUserInfoHeader.setContentType(MediaType.APPLICATION_JSON);
        requestUserInfoHeader.setBearerAuth(adminAccessToken);

        return requestUserInfoHeader;
    }
}
