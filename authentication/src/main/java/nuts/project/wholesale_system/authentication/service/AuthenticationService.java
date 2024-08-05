package nuts.project.wholesale_system.authentication.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RestTemplate restTemplate;

    private String authServerUrl = "http://localhost:8080/test";

    /**
     * It communicates with the authentication server and issues a token
     *
     * @param username The username that you send to the authentication server
     * @param password Passwords sent to the authentication server
     *                                 TODO
     * @return Token Info Map
     */
    public Map<String, Object> requestToken(String username, String password) {


        return restTemplate.postForObject(authServerUrl + "/token", null, Map.class);
    }


    /**
     * Retrieve the user information registered on the authentication server
     * TODO
     *
     * @return userInfo
     */
    public Map<String, Object> getUserTable() {
        return restTemplate.getForObject(authServerUrl + "/users", Map.class);
    }


    /**
     * Bring up jwkset for verification.
     * @return JWK set
     */
    public Object getJwtSet(){
        return restTemplate.getForObject(authServerUrl + "/set", Map.class);
    }
}
