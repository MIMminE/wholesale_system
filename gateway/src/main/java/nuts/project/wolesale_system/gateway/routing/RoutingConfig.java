package nuts.project.wolesale_system.gateway.routing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "routing")
@Getter
@Setter
public class RoutingConfig {

    private Map<String, RoutingProperty> routingTable;

    @Getter
    @Setter
    static public class RoutingProperty {

        private String url;
        private int port;
    }
}
