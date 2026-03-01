package academy.devdojo.controller;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.UserUtils;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserHardCodedRepository;
import academy.devdojo.service.UserData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class UserControllerTest {
    private static final String URL = "/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserData userData;
    @SpyBean
    private UserHardCodedRepository repository;
    private List<User> userList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserUtils userUtils;

    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("GET v1/users returns a list of users when parameter is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenFirstNameIsNull() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");

        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("GET v1/users?firstName=Ging returns a list with the found user")
    @Order(2)
    void findByName_ReturnsUserFoundInList_WhenUserIsFound() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-ging-first-name-200.json");
        var firstName = "Ging";

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users?firstName=x returns a empty list")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenUserIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("user/get-user-x-first-name-200.json");
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users/1 returns an user with given id")
    @Order(4)
    void findById_ReturnsUser_WhenUserIsFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("GET v1/users/99 throws NotFound 404 when user is not found")
    @Order(5)
    void findById_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @DisplayName("POST v1/users Creates an user")
    @Order(6)
    void save_CreatesUser_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var response = fileUtils.readResourceFile("user/post-response-user-201.json");
        var userToSave = userUtils.userToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userToSave);


        mockMvc.perform(MockMvcRequestBuilders.
                        post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("DELETE v1/users removes an user")
    @Order(7)
    void delete_RemovesAnUser_WhenSuccessful() throws Exception {
        var id = userList.getFirst().getId();
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/users/99 throws NotFound when user is not found")
    @Order(8)
    void delete_ThrowsNotFound_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @DisplayName("PUT v1/users updates an user")
    @Order(9)
    void update_UpdatesUser_WhenSucessful() throws Exception {
        var request = fileUtils.readResourceFile("user/put-request-user-200.json");
        var response = fileUtils.readResourceFile("user/put-response-user-200..json");

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/users throws NotFound when user is not found")
    @Order(10)
    void update_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("user/put-request-user-404.json");

        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.
                        put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @ParameterizedTest
    @MethodSource("postUserBadRequestSource")
    @DisplayName("POST v1/users returns bad request when fields are empty")
    @Order(11)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                        post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);

    }

    @ParameterizedTest
    @MethodSource("putUserBadRequestSource")
    @DisplayName("PUT v1/users returns bad request when fields are empty")
    @Order(12)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                        put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);

    }

    private static Stream<Arguments> postUserBadRequestSource() {
        var allRequiredErrors = allRequiredErrors();
        var invalidEmailErrors = invalidEmailErrors();

        return Stream.of(
                Arguments.of("post-request-user-blank-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-user-empty-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-user-invalid-email-400.json", invalidEmailErrors)
        );
    }

    private static Stream<Arguments> putUserBadRequestSource() {
        var allRequiredErrors = allRequiredErrors();
        allRequiredErrors.add("The field 'id' is required to update user");
        var invalidEmailErrors = invalidEmailErrors();

        return Stream.of(
                Arguments.of("put-request-user-blank-fields-400.json", allRequiredErrors),
                Arguments.of("put-request-user-empty-fields-400.json", allRequiredErrors),
                Arguments.of("put-request-user-invalid-email-400.json", invalidEmailErrors)
        );
    }

    private static List<String> invalidEmailErrors() {
        var emailInvalidError = "E-mail is not valid";

        return Collections.singletonList(emailInvalidError);
    }

    private static List<String> allRequiredErrors() {
        var firstNameRequiredError = "The field 'firstName' is required";
        var lastNameRequiredError = "The field 'lastName' is required";
        var emailRequiredError = "The field 'email' is required";

        return new ArrayList<>(List.of(firstNameRequiredError, lastNameRequiredError, emailRequiredError));
    }
}