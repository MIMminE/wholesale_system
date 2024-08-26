package nuts.project.wholesale_system.authentication.service.usecase.request_token;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.service.dto.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RequestTokenUseCaseImpl implements RequestTokenUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;

    @Override
    public TokenResponse execute(String username, String password) {
        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "password");
        requestToken.add("username", username);
        requestToken.add("password", password);
        requestToken.add("client_id", "auth-service-client");
        requestToken.add("client_secret", "iBnKyxhwMPcY7PcXDyBHqZFO6gRawg8C");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestToken, headers);

        String authServerUrl = authServerConfig.getUrl();


        return restTemplate.postForObject(authServerUrl + "/realms/auth-service/protocol/openid-connect/token", request, TokenResponse.class);
    }
}
