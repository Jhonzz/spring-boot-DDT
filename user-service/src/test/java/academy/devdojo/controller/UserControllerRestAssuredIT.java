package academy.devdojo.controller;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.config.IntegrationTestConfig;
import academy.devdojo.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.class)
class UserControllerRestAssuredIT extends IntegrationTestConfig {
    private static final String URL = "/v1/users";
    @Autowired
    private FileUtils fileUtils;
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("GET v1/users returns a list of users when parameter is null")
    @Sql(value = "/sql/user/init_four_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");
        var expectedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .and(users -> {
                    users.node("[0].id").asNumber().isPositive();
                    users.node("[1].id").asNumber().isPositive();
                    users.node("[2].id").asNumber().isPositive();
                    users.node("[3].id").asNumber().isPositive();
                });

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("[*].id")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("GET v1/users?firstName=Ging returns a list with the found user")
    @Sql(value = "/sql/user/init_one_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(2)
    void findByName_ReturnsUserFoundInList_WhenUserIsFound() {
        var expectedResponse = fileUtils.readResourceFile("user/get-user-ging-first-name-200.json");
        var firstName = "Ging";

        var response = RestAssured.given()
                .when()
                .queryParam("firstName", firstName)
                .get(URL).
                then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .and(user -> {
                    user.node("[0].id").asNumber().isPositive();
                });

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("[*].id")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("GET v1/users?firstName=x returns a empty list")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenUserIsNotFound() {
        var expectedResponse = fileUtils.readResourceFile("user/get-user-x-first-name-200.json");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("firstName", "Gon")
                .get(URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();
    }

    @Test
    @DisplayName("GET v1/users/1 returns an user with given id")
    @Sql(value = "/sql/user/init_four_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(4)
    void findById_ReturnsUserById_WhenSuccessful() {
        var expectedResponse = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var users = userRepository.findByFirstNameIgnoreCase("Ging");

        Assertions.assertThat(users).hasSize(1);

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .pathParam("id", users.getFirst().getId())
                .get(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(expectedResponse)
                .whenIgnoringPaths("id")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("GET v1/users/99 throws NotFound 404 when user is not found")
    @Order(5)
    void findById_ThrowsNotFound_WhenUserIsNotFound() {
        var expectedResponse = fileUtils.readResourceFile("user/get-response-user-by-id-404.json");
        var id = 99L;

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .get(URL + "/{id}")
                .then()
                .body(Matchers.equalTo(expectedResponse))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .log().all();
    }

    @Test
    @DisplayName("POST v1/users creates an user")
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(6)
    void saveUser_CreatesUser_WhenSuccessful() {
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");

        var expectedResponse = fileUtils.readResourceFile("user/post-response-user-201.json");

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .body(request)
                .post(URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(expectedResponse)
                .node("id")
                .asNumber()
                .isPositive();
    }

    @Test
    @DisplayName("DELETE v1/users/1 removes an user")
    @Sql(value = "/sql/user/init_one_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Order(7)
    void deleteUser_RemovesUser_WhenSuccessful() {
        var id = userRepository.findAll().getFirst().getId();

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all();
    }

    @Test
    @DisplayName("DELETE v1/users/99 throws 404 NotFound Exception")
    @Order(8)
    void deleteUser_ThrowsNotFoundException_WhenUserDoesNotExists() {
        var expectedResponse = fileUtils.readResourceFile("user/delete-response-user-by-id-404.json");
        var id = 99L;

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .pathParam("id", id)
                .delete(URL + "/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();
    }

    @Test
    @DisplayName("PUT v1/users updates an user")
    @Sql(value = "/sql/user/init_one_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Order(9)
    void update_UpdatesUser_WhenSuccessful() {
        var request = fileUtils.readResourceFile("user/put-request-user-200.json");
        var expectedResponse = fileUtils.readResourceFile("user/put-response-user-200..json");
        var users = userRepository.findByFirstNameIgnoreCase("Ging");
        request = request.replace("1", users.getFirst().getId().toString());

        Assertions.assertThat(users).hasSize(1);

        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .body(request)
                .put(URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("id")
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("PUT v1/users throws NotFoundException")
    @Order(10)
    void update_ReturnsNotFoundException_WhenUserNotFound() {
        var request = fileUtils.readResourceFile("user/put-request-user-200.json");
        var expectedResponse = fileUtils.readResourceFile("user/put-response-user-by-id-404.json");

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .body(request)
                .put(URL)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse))
                .log().all();
    }
}