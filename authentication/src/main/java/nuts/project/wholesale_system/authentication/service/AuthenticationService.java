package nuts.project.wholesale_system.authentication.service;


import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RestTemplate restTemplate;

    private String authServerUrl = "http://localhost:8888";

    /**
     * It communicates with the authentication server and issues a token
     *
     * @param username The username that you send to the authentication server
     * @param password Passwords sent to the authentication server
     *                                                                                                                                                                                 TODO
     * @return Token Info Map
     */
    public TokenResponse requestToken(String username, String password) {
        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "client_credentials");
        requestToken.add("client_id", "auth-service-client");
        requestToken.add("client_secret", "iBnKyxhwMPcY7PcXDyBHqZFO6gRawg8C");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestToken, headers);


        return restTemplate.postForObject(authServerUrl + "/realms/auth-service/protocol/openid-connect/token", request, TokenResponse.class);
    }


    /**
     * Retrieve the user information registered on the authentication server
     * TODO
     *
     * @return userInfo
     */
    public Map<String, Object> getUserTable() {


        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "client_credentials");
        requestToken.add("client_id", "admin-cli");
        requestToken.add("client_secret", "y6AOV8SqW7LqZ8e0jdkeXEu7eJq92VDy");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestToken, headers);

        String accessToken = restTemplate.postForObject(authServerUrl + "/realms/auth-service/protocol/openid-connect/token", request, TokenResponse.class).getAccessToken();

        HttpHeaders requestUserInfoHeader = new HttpHeaders();
        requestUserInfoHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestUserInfoHeader.setBearerAuth(accessToken);
        HttpEntity<Object> requestUserInfo = new HttpEntity<>(requestUserInfoHeader);

        System.out.println(accessToken);
        List body = restTemplate.exchange(authServerUrl + "/admin/realms/auth-service/users", HttpMethod.GET, requestUserInfo, List.class).getBody();
        for (Object o : body) {
            System.out.println(o.toString());
        }
        return null;
    }


    /**
     * Bring up jwkset for verification.
     *
     * @return JWK set
     */
    public Object getJwtSet() {
        return restTemplate.getForObject(authServerUrl + "/set", Map.class);
    }
}
