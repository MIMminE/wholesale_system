package nuts.project.wholesale_system.order.config;

import nuts.lib.manager.data_access_manager.DataSourceGenerator;
import nuts.lib.manager.data_access_manager.DataSourceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AdapterConfig {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Profile("prod")
    @Bean
    DataSource dataSource() {
        return DataSourceGenerator.createHikariDataSource(DataSourceType.mysql,
                "localhost", 9000, "test_db", "tester", "tester");
    }

    @Profile({"test", "integration_test"})
    @Bean
    DataSource dataSource_test() {
        return DataSourceGenerator.createHikariDataSource(DataSourceType.mysql,
                "localhost", 8811, "test_db", "root", "tester");
    }
}
