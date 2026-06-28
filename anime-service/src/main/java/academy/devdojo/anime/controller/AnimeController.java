package academy.devdojo.anime.controller;

import academy.devdojo.anime.mapper.AnimeMapper;
import academy.devdojo.anime.request.AnimePostRequest;
import academy.devdojo.anime.request.AnimePutRequest;
import academy.devdojo.anime.response.AnimeGetResponse;
import academy.devdojo.anime.response.AnimePostResponse;
import academy.devdojo.anime.response.AnimePutResponse;
import academy.devdojo.anime.service.AnimeService;
import academy.devdojo.domain.Anime;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeMapper mapper;
    private final AnimeService service;

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String animeName) {
        log.info("Request received to list all animes, param name '{}'", animeName);
        var animes = service.findAll(animeName);
        var response = mapper.toAnimeResponseList(animes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AnimeGetResponse>> findAllPaginated(@ParameterObject Pageable pageable) {
        log.info("Request received to list all animes paginated");

        var pageAnimeGetResponse = service.findAllPaginated(pageable).map(mapper::toAnimeGetResponse);
        return ResponseEntity.ok(pageAnimeGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id {}", id);

        Anime animeFound = service.findByIdOrThrowNotFoundException(id);
        AnimeGetResponse response = mapper.toAnimeGetResponse(animeFound);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> save(@RequestBody @Valid AnimePostRequest request) {
        log.info("Trying create anime: {}", request.getName());
        var anime = mapper.toAnime(request);

        Anime animeSaved = service.save(anime);

        var response = mapper.toPostAnimeResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        log.info("Deleting anime by id: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AnimePutResponse> update(@RequestBody @Valid AnimePutRequest request) {
        var animeToUpdate = mapper.toAnime(request);
        service.update(animeToUpdate);
        var response = mapper.toAnimePutResponse(animeToUpdate);
        return ResponseEntity.ok(response);
    }
}
