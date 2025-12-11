package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRepository {
    @Getter
    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        var berserk = Anime.builder().id(1L).name("Berserk").build();
        var dandadan = Anime.builder().id(2L).name("Dandadan").build();
        var dragonBall = Anime.builder().id(3L).name("Dragon ball").build();
        var naruto = Anime.builder().id(4L).name("Naruto").build();
        ANIMES.addAll(List.of(berserk, dandadan, dragonBall, naruto));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> id.equals(anime.getId())).findFirst();
    }

    public List<Anime> findByName(String name){
        return ANIMES.stream().filter(anime -> name.equalsIgnoreCase(anime.getName())).toList();
    }

    public Anime save(Anime anime){
        ANIMES.add(anime);
        return anime;
    }
    public void delete(Anime anime){
        ANIMES.remove(anime);
    }
    public void update(Anime anime){
        delete(anime);
        save(anime);
    }
}
