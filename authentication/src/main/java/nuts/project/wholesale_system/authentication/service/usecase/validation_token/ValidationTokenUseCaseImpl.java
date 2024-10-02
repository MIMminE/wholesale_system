package nuts.project.wholesale_system.authentication.service.usecase.validation_token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import nuts.project.wholesale_system.authentication.service.cache.JWKSetCache;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Component
public class ValidationTokenUseCaseImpl implements ValidationTokenUseCase {

    @Override
    public boolean execute(String token) {

        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            System.out.println("잘못된 토큰");
            return false;
        }
        String keyID = signedJWT.getHeader().getKeyID();
        JWK jwk = JWKSetCache.jwkSet.getKeyByKeyId(keyID);

        if (jwk == null) {
            System.out.println("키를 찾을수 없습니다.");
            return false;
        }
        RSAKey rsaKey = (RSAKey) jwk;
        RSAPublicKey publicKey = null;
        try {
            publicKey = rsaKey.toRSAPublicKey();
            return signedJWT.verify(new RSASSAVerifier(publicKey));
        } catch (JOSEException e) {
            System.out.println("잘못된 토큰");
            return false;
        }
    }
}
