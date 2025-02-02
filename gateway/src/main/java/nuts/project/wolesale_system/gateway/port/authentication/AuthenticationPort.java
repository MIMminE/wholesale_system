package nuts.project.wolesale_system.gateway.port.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import nuts.project.wolesale_system.gateway.port.authentication.dto.ValidationResult;

public interface AuthenticationPort {

    JWKSet getJWKSet();

    ValidationResult tokenValidation(String token);
}
