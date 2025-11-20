package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    @GetMapping()
    public List<Anime> listAllAnimes(@RequestParam(required = false) String animeName) {
        if (animeName == null) {
            return Anime.getAnimes();
        }
        return Anime.getAnimes().stream().filter(anime -> animeName.equalsIgnoreCase(anime.getName())).toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
        return Anime.
                getAnimes().
                stream().
                filter(anime -> id.equals(anime.getId())).
                findFirst().
                orElse(null);
    }

    @PostMapping()
    public Anime saveAnime(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        Anime.getAnimes().add(anime);
        return anime;
    }
}
