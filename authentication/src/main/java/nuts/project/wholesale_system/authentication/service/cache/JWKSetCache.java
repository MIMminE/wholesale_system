package nuts.project.wholesale_system.authentication.service.cache;

import com.nimbusds.jose.jwk.JWKSet;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nuts.project.wholesale_system.authentication.service.usecase.get_jwks.GetJwkSetUseCase;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class JWKSetCache {
    public static JWKSet jwkSet;
    private final GetJwkSetUseCase getJwkSetUseCase;

    @PostConstruct
    public void init() throws IOException, ParseException {
        jwkSet = getJwkSetUseCase.execute();
    }

}
