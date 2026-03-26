package academy.devdojo.anime;

import academy.devdojo.anime.repository.AnimeRepository;
import academy.devdojo.anime.service.AnimeService;
import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service; //classe alvo dos testes
    @Mock
    private AnimeRepository repository; //mockar pois esta dentro do service
    private List<Anime> animeList;
    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {
        animeList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("FindAll returns all animes when parameter is null")
    @Order(1)
    void findByName_ReturnsAllAnimes_WhenParameterIsNull(){
        BDDMockito.when(repository.findAll()).thenReturn(animeList);

        var animes = service.findAll(null);

        Assertions.assertThat(animes).isNotEmpty().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("FindByName returns anime with given name")
    @Order(2)
    void findByName_ReturnsFoundAnime_WhenParameterIsNotNull(){
        var anime = animeList.getFirst();
        var expectedAnime = Collections.singletonList(anime);
        BDDMockito.when(repository.findByName(anime.getName())).thenReturn(expectedAnime);

        var animesFound = service.findAll(anime.getName());
        Assertions.assertThat(animesFound).contains(anime);
    }

    @Test
    @DisplayName("FindByName returns empty list when anime is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        var name = "not_found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var emptyList = service.findAll(name);
        Assertions.assertThat(emptyList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("FindById returns anime with given id")
    @Order(4)
    void findById_ReturnsAnime_WhenParameterIsNotNull(){
        var anime = animeList.getFirst();
        BDDMockito.when(repository.findById(anime.getId())).thenReturn(Optional.of(anime));

        var expectedAnime = service.findByIdOrThrowNotFoundException(anime.getId());
        Assertions.assertThat(expectedAnime).isEqualTo(anime);
    }

    @Test
    @DisplayName("FindById throws ResponseStatusException when anime is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound(){
        var anime = animeList.getFirst();
        BDDMockito.when(repository.findById(anime.getId())).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> service.findByIdOrThrowNotFoundException(anime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("Save creates an anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful(){
        var anime = Anime.builder().id(99L).name("hunterxhunter").build();
        BDDMockito.when(repository.save(anime)).thenReturn(anime);

        var savedAnime = service.save(anime);
        Assertions.assertThat(anime).isEqualTo(savedAnime).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Delete removes an anime")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessful(){
        var anime = animeList.getFirst();
        BDDMockito.when(repository.findById(anime.getId())).thenReturn(Optional.of(anime));

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(anime.getId()));
    }

    @Test
    @DisplayName("Delete throws ResponseStatusException when anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound(){
        var anime = animeList.getFirst();
        BDDMockito.when(repository.findById(anime.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.delete(anime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
    @Test
    @DisplayName("Update updates anime")
    @Order(9)
    void update_UpdatesAnime_WhenSuccessful(){
        var anime = animeList.getFirst();
        anime.setName("One piece");
        BDDMockito.when(repository.findById(anime.getId())).thenReturn(Optional.of(anime));

        service.update(anime);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(anime));
    }

    @Test
    @DisplayName("Update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound(){
        var anime = animeList.getFirst();
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(anime))
                .isInstanceOf(ResponseStatusException.class);
    }

}