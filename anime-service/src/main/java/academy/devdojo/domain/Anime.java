package academy.devdojo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Anime {
    private long id;
    private String name;

    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        Anime berserk = Anime.builder().id(1L).name("Berserk").build();
        Anime dandadan = Anime.builder().id(2L).name("Dandadan").build();
        Anime dragonBall = Anime.builder().id(3L).name("Dragon ball").build();
        Anime naruto = Anime.builder().id(4L).name("Naruto").build();
        animes.addAll(List.of(berserk, dandadan, dragonBall, naruto));
    }
}
