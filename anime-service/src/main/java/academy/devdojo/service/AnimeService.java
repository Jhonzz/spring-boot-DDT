package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {
    AnimeHardCodedRepository repository;

    public AnimeService() {
        this.repository = new AnimeHardCodedRepository();
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public List<Anime> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Anime findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public void delete(Long id) {
        var animeFound = findByIdOrThrowNotFoundException(id);
        repository.delete(animeFound);
    }
    public void update(Anime animeToUpdate){
        assertAnimeExists(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }

    public void assertAnimeExists(Long id){
        findByIdOrThrowNotFoundException(id);
    }

}
