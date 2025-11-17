package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Anime {
    private long id;
    private String name;

    public static List<Anime> animeList() {
        Anime berserk = new Anime(1L, "Berserk");
        Anime dandadan = new Anime(2L, "Dandadan");
        Anime dragonBall = new Anime(3L, "Dragon ball");
        Anime naruto = new Anime(4L, "Naruto");

        return List.of(berserk, dandadan, dragonBall, naruto);
    }
}
