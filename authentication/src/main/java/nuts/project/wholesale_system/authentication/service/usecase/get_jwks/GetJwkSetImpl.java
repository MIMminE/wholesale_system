package nuts.project.wholesale_system.authentication.service.usecase.get_jwks;

import com.nimbusds.jose.jwk.JWKSet;
import nuts.project.wholesale_system.authentication.config.AuthServerProperties;
import nuts.project.wholesale_system.authentication.service.dto.JwkSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

@Component
public class GetJwkSetImpl implements GetJwkSetUseCase {

    private final RestTemplate restTemplate;
    private final AuthServerProperties authServerConfig;

    public GetJwkSetImpl(RestTemplate restTemplate, AuthServerProperties authServerConfig) {
        this.restTemplate = restTemplate;
        this.authServerConfig = authServerConfig;
    }

    @Override
    public JWKSet execute() throws IOException, ParseException {
        String requestUrl = String.format("%s/realms/master/protocol/openid-connect/certs", authServerConfig.getUrl());
        JWKSet jwkSet = JWKSet.load(new URL(requestUrl));
        jwkSet.getKeyByKeyId()
        return null;
    }
}
