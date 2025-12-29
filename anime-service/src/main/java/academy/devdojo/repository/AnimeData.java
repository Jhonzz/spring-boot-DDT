package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    @Getter
    private final List<Anime> animes = new ArrayList<>();

    {
        var berserk = Anime.builder().id(1L).name("Berserk").build();
        var dandadan = Anime.builder().id(2L).name("Dandadan").build();
        var dragonBall = Anime.builder().id(3L).name("Dragon ball").build();
        var naruto = Anime.builder().id(4L).name("Naruto").build();
        animes.addAll(List.of(berserk, dandadan, dragonBall, naruto));
    }
}
