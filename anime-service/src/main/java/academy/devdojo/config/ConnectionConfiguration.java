package academy.devdojo.config;

import externalDependency.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ConnectionConfiguration {
    @Value("${database.url}") //will get from the yml for example database.mongo.url, using defined in run config
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Bean
    @Profile("mysql")
    public Connection connectionMySql(){
        return new Connection(url, username,password);
    }

    @Bean(value = "connectionMongoDB")
    @Profile("mongo")
    // @Primary
    public Connection connection(){
        return new Connection(url, username, password);
    }
}
