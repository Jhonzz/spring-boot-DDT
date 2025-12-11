package academy.devdojo.controller;

import academy.devdojo.DTO.request.AnimePostRequest;
import academy.devdojo.DTO.request.AnimePutRequest;
import academy.devdojo.DTO.response.AnimeGetResponse;
import academy.devdojo.DTO.response.AnimePostResponse;
import academy.devdojo.DTO.response.AnimePutResponse;
import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<AnimeGetResponse>> listAllAnimes(@RequestParam(required = false) String animeName) {

        var animes = service.findAll(animeName);
        var response = mapper.toAnimeResponseList(animes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id {}", id);

        Anime animeFound = service.findByIdOrThrowNotFoundException(id);
        AnimeGetResponse response = mapper.toAnimeResponse(animeFound);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Trying create anime: {}", request.getName());
        var anime = mapper.toAnime(request);

        Anime animeSaved = service.save(anime);

        var response = mapper.toPostAnimeResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id){
        log.info("Deleting anime by id: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AnimePutResponse> update(@RequestBody AnimePutRequest request){
        var animeToUpdate = mapper.toAnime(request);
        service.update(animeToUpdate);
        AnimePutResponse response = mapper.toAnimePutResponse(animeToUpdate);
        return ResponseEntity.ok(response);
    }
}
