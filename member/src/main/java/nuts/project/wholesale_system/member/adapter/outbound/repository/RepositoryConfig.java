package nuts.project.wholesale_system.member.adapter.outbound.repository;

import nuts.lib.manager.data_access_manager.DataSourceGenerator;
import nuts.lib.manager.data_access_manager.DataSourceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    @Bean
    DataSource dataSource() {
        return DataSourceGenerator.createHikariDataSource(DataSourceType.mysql,
                "localhost", 9000, "test_db", "tester", "tester");
    }
}
