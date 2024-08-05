package nuts.project.wholesale_system.order.adapter.outbound.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile({"prod", "integration"})
public class JWKSConfig {

    private JWKSet jwkSet;

    private String authenticationServiceUrl = "/authentication-service";

    @PostConstruct
    void JWKSetInit() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> jwtResult = restTemplate.getForObject(authenticationServiceUrl + "/jwt-sets", ResponseEntity.class);
        JWKSet body = (JWKSet) jwtResult.getBody();
    }
}
