package academy.devdojo.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import static academy.devdojo.commons.Constants.*;

@TestConfiguration
@Lazy
public class RestAssuredConfig {

    @LocalServerPort
    int port;

    @Bean(name = "requestSpecificationRegularUser")
    public RequestSpecification requestSpecificationRegularUser(){
         return RestAssured.given()
                 .baseUri(BASE_URI + port)
                 .auth().basic(REGULAR_USERNAME, PASSWORD);
         //"preemptive" do the authentication and the request, with just "auth" he tries, returns error 401 and try again authenticated
    }

    @Bean(name = "requestSpecificationAdminUser")
    public RequestSpecification requestSpecificationAdminUser(){
        return RestAssured.given()
                .baseUri(BASE_URI + port)
                .auth().basic(ADMIN_USERNAME, PASSWORD);
        //"preemptive" do the authentication and the request, with just "auth" he tries, returns error 401 and try again authenticated
    }
}
