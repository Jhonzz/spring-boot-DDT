package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Anime {
    private long id;
    private String name;

    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        Anime berserk = new Anime(1L, "Berserk");
        Anime dandadan = new Anime(2L, "Dandadan");
        Anime dragonBall = new Anime(3L, "Dragon ball");
        Anime naruto = new Anime(4L, "Naruto");
        animes.addAll(List.of(berserk, dandadan, dragonBall, naruto));
    }
}
