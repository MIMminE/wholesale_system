package nuts.project.wolesale_system.gateway.routing;

import com.nimbusds.jose.JOSEException;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenService;
import nuts.lib.manager.security_manager.authentication.token.jwt.JwtTokenServiceBuilder;
import nuts.lib.manager.security_manager.authentication.token.jwt.algoritim.HMACSupport;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class GatewayConfig {

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("/test", r -> r.path("/orders/harunuts")
                        .filters(f -> f.filter((exchange, chain) -> {
                            System.out.println(exchange.getRequest().getHeaders());
                            return chain.filter(exchange);
                        })).uri(URI.create("http://localhost:9004/")))

                .route("token", r -> r.path("/token").uri("http://localhost:9001/"))
                .build();
    }

    @Bean
    JwtTokenService jwtTokenService() throws JOSEException {
        return new JwtTokenServiceBuilder()
                .HMACSigner(HMACSupport.HS256, "hellohellohellohellohellohellohellohellohellohellohello").build();
    }


}

