package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {
    public List<Anime> newAnimeList(){
        var hunterxHunter = Anime.builder().id(1L).name("HunterxHunter").build();
        var naruto = Anime.builder().id(2L).name("Naruto").build();
        var onePiece = Anime.builder().id(3L).name("One piece").build();
        var dandadan = Anime.builder().id(4L).name("Dandadan").build();
        return new ArrayList<>(List.of(hunterxHunter, naruto, onePiece, dandadan));
    }

    public Anime newAnimeToSave(){
        return Anime.builder().id(99L).name("Drifters").build();
    }
}
