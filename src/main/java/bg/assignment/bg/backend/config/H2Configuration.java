package bg.assignment.bg.backend.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Configuration
{
	@Bean(name = "h2ds")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource gameDataSource()
	{
		final var dataSource =  DataSourceBuilder.create().build();
		return dataSource;
	}
}
