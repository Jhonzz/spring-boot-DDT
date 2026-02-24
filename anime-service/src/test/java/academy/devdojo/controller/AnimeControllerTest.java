package academy.devdojo.controller;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.commons.FileUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
public class AnimeControllerTest {
    private static final String URL = "/v1/animes";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AnimeData animeData;
    @MockitoSpyBean
    private AnimeHardCodedRepository repository;
    private List<Anime> animeList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animeList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("GET v1/animes returns a list of animes when parameter is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenParameterIsNull() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-null-name-200.json");

        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?animeName=huntexhunter returns a list with the found anime")
    @Order(2)
    void findByName_ReturnsAnimeFoundInList_WhenAnimeIsFound() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-hunterxhunter-name-200.json");
        var name = "hunterxhunter";

        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("animeName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?animeName=x returns a empty list")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-x-name-200.json");
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("animeName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/1 returns an anime found")
    @Order(4)
    void findById_ReturnsAnimeFound_WhenAnimeIsFound() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-by-id-200.json");
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", animeList.getFirst().getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 throws ResponseStatusException when not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @DisplayName("POST v1/animes creates an Anime")
    @Order(6)
    void save_CreatesAnime_WhenSucessful() throws Exception {
        var request = fileUtils.readResourceFile("anime/post-request-anime-200.json");
        var response = fileUtils.readResourceFile("anime/post-response-anime-201.json");
        var animeToSave = animeUtils.newAnimeToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("DELETE V1/animes/1 removes an anime")
    @Order(7)
    void delete_RemovesAnimeFound_WhenSucessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/99 throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @DisplayName("PUT v1/animes updates an Anime")
    @Order(9)
    void update_UpdatesAnime_WhenSucessful() throws Exception {
        var request = fileUtils.readResourceFile("anime/put-request-anime-200.json");
        var response = fileUtils.readResourceFile("anime/put-response-anime-200.json");

        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

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
    @DisplayName("PUT v1/animes throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("anime/put-request-anime-404.json");

        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.
                        put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }
}
