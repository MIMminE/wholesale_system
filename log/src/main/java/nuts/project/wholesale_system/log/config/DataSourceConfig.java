package nuts.project.wholesale_system.log.config;

import nuts.lib.manager.data_access_manager.DataSourceGenerator;
import nuts.lib.manager.data_access_manager.DataSourceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    DataSource dataSource(){
        return DataSourceGenerator.createHikariDataSource(DataSourceType.mysql,
                "localhost", 8501, "log_db", "root","tester");
    }

}
