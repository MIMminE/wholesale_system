package nuts.project.wholesale_system.authentication.service;


import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.controller.response.TokenResponse;
import nuts.project.wholesale_system.authentication.controller.response.UserInformation;
import nuts.project.wholesale_system.authentication.exception.AuthenticationException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RestTemplate restTemplate;

    private String authServerUrl = "http://localhost:8888";

    @Override
    public TokenResponse requestToken(String userName, String password) {
        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "password");
        requestToken.add("username", userName);
        requestToken.add("password", password);
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
    @Override
    public List<UserInformation> getUserTable() {

        String adminAccessToken = getAdminAccessToken();

        HttpHeaders requestUserInfoHeader = new HttpHeaders();
        requestUserInfoHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestUserInfoHeader.setBearerAuth(adminAccessToken);
        HttpEntity<Object> requestUserInfo = new HttpEntity<>(requestUserInfoHeader);

        return restTemplate.exchange(authServerUrl + "/admin/realms/auth-service/users", HttpMethod.GET, requestUserInfo, new ParameterizedTypeReference<List<UserInformation>>() {
        }).getBody();
    }

    @Override
    public UserInformation getUser(String username) throws AuthenticationException {
        List<UserInformation> userTable = getUserTable();
        return userTable.stream().filter(user -> Objects.equals(user.getUserName(), username)).findFirst().orElseThrow(()
                -> new AuthenticationException(AuthenticationException.AuthenticationExceptionType.USER_NOT_FOUND));
    }


    @Override
    public UserInformation registerUser(String username, String email, String password) {

        String adminAccessToken = getAdminAccessToken();

        HttpHeaders requestUserInfoHeader = new HttpHeaders();
        requestUserInfoHeader.setContentType(MediaType.APPLICATION_JSON);
        requestUserInfoHeader.setBearerAuth(adminAccessToken);

        HashMap<String, Object> requestRegister = new HashMap<>();
        requestRegister.put("username", username);
        requestRegister.put("email", email);
        requestRegister.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("value", password);
        requestRegister.put("credentials", List.of(credentials));


        HttpEntity<Object> requestUserInfo = new HttpEntity<>(requestRegister, requestUserInfoHeader);


        restTemplate.postForEntity(authServerUrl + "/admin/realms/auth-service/users", requestUserInfo, null);

        return null;
    }


    /**
     * Bring up jwkset for verification.
     *
     * @return JWK set
     */
    @Override
    public Object getJwkSet() {
        return restTemplate.getForObject(authServerUrl + "/set", Map.class);
    }

    private String getAdminAccessToken() {
        MultiValueMap<String, String> requestToken = new LinkedMultiValueMap<>();
        requestToken.add("grant_type", "client_credentials");
        requestToken.add("client_id", "admin-cli");
        requestToken.add("client_secret", "y6AOV8SqW7LqZ8e0jdkeXEu7eJq92VDy");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestToken, headers);

        String accessToken = restTemplate.postForObject(authServerUrl + "/realms/auth-service/protocol/openid-connect/token", request, TokenResponse.class).getAccessToken();
        return accessToken;
    }
}
