package ca.sheridancollege.sprint2.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean(name="H2-DataSource")
    @Primary
    public DataSource loadSchema(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:newsecuritysetup.sql")
                .build();
    }

    @Bean(name="H2JDBC")
    @DependsOn("H2-DataSource")
    @Primary
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("H2-DataSource") DataSource ds){
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean(name = "RDS-DataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name= "RemoteJDBC")
    @DependsOn("RDS-DataSource")
    public NamedParameterJdbcTemplate jdbcRemote(@Qualifier("RDS-DataSource") DataSource ds){
        return new NamedParameterJdbcTemplate(ds);
    }
//     @Bean
//    @ConfigurationProperties(prefix = "spring.second-datasource")
//    public DataSourceProperties secondaryDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//    @Bean
//    @ConfigurationProperties("spring.second-datasource.hikari")
//    public HikariDataSource secondaryDataSource(DataSourceProperties properties){
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
}
