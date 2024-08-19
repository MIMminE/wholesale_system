package nuts.project.wholesale_system.authentication.service.usecase.get_jwks;

import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.controller.response.JwkSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetJwkSetImpl implements GetJwkSetUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;

    public GetJwkSetImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
    }

    @Override
    public JwkSet execute() {

        String requestUrl = String.format("%s/realms/master/protocol/openid-connect/certs", authServerConfig.getUrl());
        return restTemplate.getForObject(requestUrl, JwkSet.class);
    }
}
