package nuts.project.wholesale_system.authentication.service.usecase.validation_token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import nuts.project.wholesale_system.authentication.service.cache.JWKSetCache;
import nuts.project.wholesale_system.authentication.service.dto.ValidationResponse;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

@Component
public class ValidationTokenUseCaseImpl implements ValidationTokenUseCase {

    @Override
    public ValidationResponse execute(String token) {

        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            System.out.println("잘못된 토큰");
            return new ValidationResponse(false, null);
        }
        String keyID = signedJWT.getHeader().getKeyID();
        JWK jwk = JWKSetCache.jwkSet.getKeyByKeyId(keyID);

        if (jwk == null) {
            System.out.println("키를 찾을수 없습니다.");
            return new ValidationResponse(false, null);
        }
        RSAKey rsaKey = (RSAKey) jwk;
        RSAPublicKey publicKey = null;
        try {
            publicKey = rsaKey.toRSAPublicKey();
            System.out.println(signedJWT);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            System.out.println(expirationTime);
            return new ValidationResponse(signedJWT.verify(new RSASSAVerifier(publicKey)), null);
        } catch (JOSEException | ParseException e) {
            System.out.println("잘못된 토큰");
            return new ValidationResponse(false, null);
        }
    }
}
