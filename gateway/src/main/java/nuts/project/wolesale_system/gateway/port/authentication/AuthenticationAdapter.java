package nuts.project.wolesale_system.gateway.port.authentication;

import lombok.RequiredArgsConstructor;
import nuts.project.wolesale_system.gateway.port.authentication.dto.ValidationResult;
import nuts.project.wolesale_system.gateway.routing.RoutingConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static nuts.project.wolesale_system.gateway.exception.GatewayException.GatewayExceptionCode.AuthenticationFail;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationPort {

    private final RestTemplate restTemplate;
    private final RoutingConfig routingConfig;

    @Override
    public ValidationResult tokenValidation(String token) {

        RoutingConfig.RoutingProperty routingProperty = routingConfig.getRoutingTable().get("authentication-service");
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:9004/authentication-service/validation/%s".formatted(token), Object.class);
        System.out.println(response);

        return new ValidationResult("admin", "testUser", true);
    }
}
