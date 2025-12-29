package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;
    @Getter
    private List<Anime> animeList;


    @BeforeEach
    void init() {
        var berserk = Anime.builder().id(1L).name("Berserk").build();
        var dandadan = Anime.builder().id(2L).name("Dandadan").build();
        var dragonBall = Anime.builder().id(3L).name("Dragon ball").build();
        var naruto = Anime.builder().id(4L).name("Naruto").build();
        animeList = new ArrayList<>(List.of(berserk, dandadan, dragonBall, naruto));
    }

    @Test
    @DisplayName("findAll returns all animes when sucessful")
    @Order(1)
    void findAll_returnsAllAnimes_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findAll();
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("findById returns anime with given id")
    @Order(2)
    void findByName_returnsA_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();
        var animeFound = repository.findById(expectedAnime.getId());

        Assertions.assertThat(animeFound).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findById returns anime with given id")
    @Order(2)
    void findById_returnsAnimeById_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var expectedAnime = animeList.getFirst();
        var animeFound = repository.findById(expectedAnime.getId());

        Assertions.assertThat(animeFound).isPresent().contains(expectedAnime);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_returnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animes = repository.findByName(null);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns a list with given name")
    @Order(4)
    void findByName_returnsListWithAnimes_WhenSucessfull(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animeName = animeList.getFirst().getName();
        var expectedAnime = repository.findByName(animeName);

        Assertions.assertThat(animeList).contains(expectedAnime.getFirst());
    }

    @Test
    @DisplayName("saveAnime returns a list with given name")
    @Order(5)
    void saveAnime_createsAnime_WhenSucessful() {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var anime = Anime.builder().id(5L).name("MAPPA").build();
        var createdAnime = repository.save(anime);

        Assertions.assertThat(anime).hasNoNullFieldsOrProperties();
        Assertions.assertThat(animeList).contains(createdAnime);
    }

    @Test
    @DisplayName("update updates an anime")
    @Order(5)
    void updateAnime_updatesAnime_WhenSucessful(){
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("MAPPA");
        repository.update(animeToUpdate);

        Assertions.assertThat(animeList).contains(animeToUpdate);

        var animeFound = repository.findById(animeToUpdate.getId());

        Assertions.assertThat(animeFound).isPresent();
        Assertions.assertThat(animeFound.get().getName()).isEqualTo(animeFound.get().getName());
    }


}