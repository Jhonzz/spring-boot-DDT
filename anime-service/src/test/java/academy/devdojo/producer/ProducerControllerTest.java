package academy.devdojo.producer;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.ProducerUtils;
import academy.devdojo.domain.Producer;
import academy.devdojo.producer.controller.ProducerController;
import academy.devdojo.producer.repository.ProducerHardCodedRepository;
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
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = ProducerController.class)
//Não carrega as classes @Component, então voce precisa adiciona-las no @import
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"academy.devdojo.producer", "academy.devdojo.commons"}) // will scan all the beans in the package
//@Import({ProducerMapperImpl.class, ProducerService.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class}) can be used in a application with excessive beans, so the process would cost less
//@ActiveProfiles("test") //inputed by pom.xml with surefire (best way to do this to not forget)
class ProducerControllerTest {
    private static final String URL = "/v1/producers";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerData producerData;
    @SpyBean
    private ProducerHardCodedRepository repository; //using in post
    private List<Producer> producerList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producerList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("GET v1/producers returns a list with all producers when argument is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenParameterIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?producerName=Ufotable returns a list with found object when name exists")
    @Order(2)
    void findAll_ReturnsProducerFoundInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name = "Ufotable";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("producerName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?producerName=x returns a empty list when parameter is null")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("producerName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/1 returns a producer when id exists")
    @Order(4)
    void findById_ReturnsProducer_WhenProducerIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/999 throws NotFound 404 when producer is not found")
    @Order(5)
    void findById_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/get-producer-by-id-404.json");
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var id = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("POST v1/producers creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSucessful() throws Exception {
        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var response = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var producerToSave = producerUtils.newProducerToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) //its not necessary, but force the test use this type
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producers updates a producer")
    @Order(7)
    void update_UpdatesProducer_WhenSucessful() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");
        var response = fileUtils.readResourceFile("producer/put-response-producer-200.json");

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) //its not necessary, but force the test use this type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producers throws NotFound when producer is not found")
    @Order(8)
    void update_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
        var response = fileUtils.readResourceFile("producer/put-response-producer-404.json");

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("DELETE v1/producers/1 removes a producer")
    @Order(9)
    void delete_RemovesAProducer_WhenSucessful() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        Long id = producerList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/producers/99 throws NotFound when producer is not found")
    @Order(10)
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("producer/delete-response-producer-404.json");

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        Long id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @ParameterizedTest
    @MethodSource("postProducerBadRequest")
    @DisplayName("POST v1/producers return bad request when fields are empty")
    @Order(11)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", "v1")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("putProducerBadRequest")
    @DisplayName("PUT v1/producers return bad request when fields are empty")
    @Order(12)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
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

    private static Stream<Arguments> postProducerBadRequest() {
        var allRequiredErrors = allRequiredErrors();

        return Stream.of(
                Arguments.of("post-request-producer-blank-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-producer-empty-fields-400.json", allRequiredErrors)
        );
    }

    private static Stream<Arguments> putProducerBadRequest() {
        var allRequiredErrors = allRequiredErrors();
        allRequiredErrors.add("Field 'id' is required when update producer");

        return Stream.of(
                Arguments.of("put-request-producer-blank-fields-400.json", allRequiredErrors),
                Arguments.of("put-request-producer-empty-fields-400.json", allRequiredErrors)
        );
    }


    private static List<String> allRequiredErrors() {
        var requiredNameError = "Field 'name' is required";

        return new ArrayList<>(List.of(requiredNameError));
    }
}