package nuts.project.wholesale_system.order.adapter.outbound.authentication;

import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.order.domain.ports.authentication.AuthenticationPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationPort {

    private final RestTemplate restTemplate;

    @Override
    public Map<String, Object> getJWTSet() {
        return Map.of();
    }
}
