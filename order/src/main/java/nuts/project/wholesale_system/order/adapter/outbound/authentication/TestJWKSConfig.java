package nuts.project.wholesale_system.order.adapter.outbound.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestJWKSConfig {

    @Bean
    JWKSet jwkSet() {
        OctetSequenceKey key = new OctetSequenceKey.Builder(new byte[32])
                .keyID("test-key")
                .build();

        return new JWKSet(key);
    }
}
