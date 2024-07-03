package nuts.project.wholesale_system.authentication.security_config;

import com.nimbusds.jose.JOSEException;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenServiceBuilder;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.HMACSupport;
import nuts.project.wholesale_system.authentication.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(auth-> auth.requestMatchers("/jwt_auth/**").authenticated()
                        .anyRequest().permitAll());

        return http.build();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter() throws JOSEException {
        return new JwtAuthenticationFilter("/jwt_auth/**", jwtTokenService());
    }

    @Bean
    JwtTokenService jwtTokenService() throws JOSEException {
        JwtTokenService jwtTokenService = new JwtTokenServiceBuilder()
                .HMACSigner(HMACSupport.HS256, "hellohellohellohellohellohellohellohellohellohellohello").build();
        return jwtTokenService;
    }
}
