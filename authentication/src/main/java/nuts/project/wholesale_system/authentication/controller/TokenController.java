package nuts.project.wholesale_system.authentication.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenService service;

    @GetMapping("/token")
    public String test() throws JOSEException {

        JWTClaimsSet jwt = new JWTClaimsSet.Builder()
                .claim("hello", "hello")
                .issuer("test!")
                .build();

        return service.createToken(jwt).serialize();
    }
}
