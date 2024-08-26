package nuts.project.wholesale_system.authentication.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "auth-server")
public class AuthServerProperties {
    private String url;
    private String realms;
    private String clientId;
    private String clientSecret;
}
