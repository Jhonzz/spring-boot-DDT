package academy.devdojo.config;

import externalDependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {
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
