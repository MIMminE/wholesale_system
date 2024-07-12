package nuts.project.wholesale_system.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.config")
public class LoggerConfigProperties {
    private List<ConsumerInformation> consumerInformationList;

    private String name;

    @Data
    static public class ConsumerInformation{
        private String queueName;
        private String db_source;

    }
}
