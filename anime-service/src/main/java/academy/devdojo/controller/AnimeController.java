package academy.devdojo.controller;

import academy.devdojo.DTO.request.AnimePostRequest;
import academy.devdojo.DTO.response.AnimeGetResponse;
import academy.devdojo.DTO.response.AnimePostResponse;
import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String animeName) {
        List<AnimeGetResponse> animeResponseList = MAPPER.toAnimeResponseList(Anime.getAnimes());
        if (animeName == null) return ResponseEntity.ok(animeResponseList);

        List<AnimeGetResponse> animeFilter = animeResponseList.stream().filter(anime -> animeName.equalsIgnoreCase(anime.getName())).toList();

        return ResponseEntity.ok(animeFilter);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id {}", id);

        var response = Anime.
                getAnimes().
                stream().
                filter(anime -> id.equals(anime.getId())).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        return ResponseEntity.ok(MAPPER.toAnimeResponse(response));
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> saveAnime(@RequestBody AnimePostRequest animeRequest) {
        log.info("Trying create anime: {}", animeRequest.getName());
        var anime = MAPPER.toAnime(animeRequest);
        Anime.getAnimes().add(anime);

        var response = MAPPER.toPostAnimeResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id){
        log.info("Deleting anime by id: {}", id);

        var animeToDelete = Anime.getAnimes().
                stream().
                filter(anime -> id.equals(anime.getId())).
                findFirst().
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        Anime.getAnimes().remove(animeToDelete);
        return ResponseEntity.noContent().build();
    }
}
