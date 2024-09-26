package nuts.project.wholesale_system.authentication.service.usecase.get_jwks;

import com.nimbusds.jose.jwk.JWKSet;
import nuts.project.wholesale_system.authentication.service.dto.JwkSet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

public interface GetJwkSetUseCase {

    JWKSet execute() throws IOException, ParseException;

}
