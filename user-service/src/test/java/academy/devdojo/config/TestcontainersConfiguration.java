package academy.devdojo.config;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
@Profile("itest")
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    @RestartScope
    MySQLContainer<?> mysqlContainer(){
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));
    }
}