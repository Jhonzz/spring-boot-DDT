package academy.devdojo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class ConnectionBeanConfiguration {
    private final ConnectionConfigurationProperties configurationProperties;
//    @Value("${database.url}") //will get from the yml for example database.mongo.url, using defined in run config
//    private String url;
//    @Value("${database.username}")
//    private String username;
//    @Value("${database.password}")
//    private String password;

    @Bean
//    @Profile("mysql")
    @Primary
    public Connection connectionMySql() {
        return new Connection(configurationProperties.url(),
                configurationProperties.username(),
                configurationProperties.password());
    }

    @Bean(value = "connectionMongoDB")
    @Profile("mongo")
    // @Primary
    public Connection connection() {
        return new Connection(configurationProperties.url(),
                configurationProperties.username(),
                configurationProperties.password());
    }
}
