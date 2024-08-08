package nuts.project.wolesale_system.gateway.port.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import nuts.project.wolesale_system.gateway.exception.GatewayException;
import nuts.project.wolesale_system.gateway.port.authentication.dto.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static nuts.project.wolesale_system.gateway.exception.GatewayException.GatewayExceptionCode.AuthenticationFail;

@Component
public class AuthenticationAdapter implements AuthenticationPort {

    private final JWKSet jwkSet = getJWKSet();

    @Override
    public JWKSet getJWKSet() {
        return new JWKSet();
    }

    @Override
    public ValidationResult tokenValidation(String token) {

        // jwt 토큰 검증으로 권한 추출
        if (token == "error")
            throw new GatewayException(AuthenticationFail);

        System.out.println(token + " token validation!");

        return new ValidationResult("admin", "testUser",true);
    }
}
