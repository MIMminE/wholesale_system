package nuts.project.wolesale_system.gateway.filter;

import nuts.project.wolesale_system.gateway.port.authentication.AuthenticationPort;
import org.springframework.cloud.gateway.filter.GatewayFilter;


public abstract class TokenValidationFilter implements GatewayFilter {
    protected final String requiredRole;
    protected final AuthenticationPort authenticationPort;

    public TokenValidationFilter(String requiredRole, AuthenticationPort authenticationPort) {
        this.requiredRole = requiredRole;
        this.authenticationPort = authenticationPort;
    }
}
