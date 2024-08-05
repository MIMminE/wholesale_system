package nuts.project.wholesale_system.order.adapter.outbound.authentication;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@ActiveProfiles("test")
@SpringBootTest
class AuthenticationAdapterTest {

    @Autowired
    JWKSet jwkSet;


    @DisplayName("")
    @Test
    void test() {
        // given
        List<JWK> keys = jwkSet.getKeys();
        for (JWK key : keys) {
            System.out.println(key);
        }
        // when

        // then
    }


}