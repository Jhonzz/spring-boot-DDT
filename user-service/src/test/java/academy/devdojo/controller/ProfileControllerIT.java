package academy.devdojo.controller;

import academy.devdojo.DTO.response.ProfileGetResponse;
import academy.devdojo.DTO.response.ProfilePostResponse;
import academy.devdojo.commons.FileUtils;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileControllerIT {
    private static final String URL = "/v1/profiles";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private FileUtils fileUtils;


    @Test
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Order(1)
    @Sql(value = "/sql/init_two_profiles.sql")
    void findAll_ReturnsAllProfiles_WhenSuccessful() {
        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {};
        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.GET, null, typeReference);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull().doesNotContainNull().hasSize(2);

        responseEntity
                .getBody()
                .forEach(profile -> Assertions.assertThat(profile).hasNoNullFieldsOrProperties());
    }

    @Test
    @DisplayName("GET v1/profiles returns a empty list when nothing is found")
    @Order(2)
    void findAll_ReturnsEmptyList_WhenNothingIsFound() {
        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {};
        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.GET, null, typeReference);

        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(responseEntity.getBody())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("POST v1/profiles creates a profile")
    @Order(3)
    void save_CreatesProfile_WhenSuccessful() throws Exception {
        var profileToSave = fileUtils.readResourceFile("profile/post-request-profile-200.json");
        var profileHttpRequest = buildHttpEntity(profileToSave);

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.POST, profileHttpRequest, ProfilePostResponse.class);

        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        Assertions
                .assertThat(responseEntity.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @MethodSource("postProfileBadRequestSource")
    @DisplayName("POST v1/profiles returns bad request when fields are invalid")
    @Order(4)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String requestFile, String responseFile) throws Exception {
        var request = fileUtils.readResourceFile("profile/%s".formatted(requestFile));
        var expectedResponse = fileUtils.readResourceFile("profile/%s".formatted(responseFile));
        var profileEntityRequest = buildHttpEntity(request);

        var responseEntity = testRestTemplate.exchange(URL, HttpMethod.POST, profileEntityRequest, String.class);

        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions
                .assertThat(responseEntity)
                .isNotNull();

        JsonAssertions.assertThatJson(responseEntity.getBody())
                .whenIgnoringPaths("timestamp") //ignore timestamp (because it will always change)
                .isEqualTo(expectedResponse);
    }

    private static Stream<Arguments> postProfileBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-profile-empty-fields-400.json", "post-response-profile-empty-fields-400.json"),
                Arguments.of("post-request-profile-blank-fields-400.json", "post-response-profile-blank-fields-400.json")
        );
    }

    private static HttpEntity<String> buildHttpEntity(String request) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(request, httpHeaders);
    }

}
