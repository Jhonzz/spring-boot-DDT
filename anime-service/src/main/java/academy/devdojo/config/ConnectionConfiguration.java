package academy.devdojo.config;

import externalDependency.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {
    @Value("${database.url") //will get from the yml for example database.mongo.url, using defined in run config
    private String url;
    @Value("${database.username")
    private String username;
    @Value("${database.password")
    private String password;

    @Bean
    public Connection connectionMySql(){
        return new Connection("localhost", "devdojoMySql","vegeta");
    }

    @Bean(value = "connectionMongoDB")
    // @Primary
    public Connection connection(){
        return new Connection("localhostMongoDB", "devdojoMongoDB", "Goku");
    }
}
