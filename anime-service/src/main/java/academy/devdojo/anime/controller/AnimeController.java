package academy.devdojo.anime.controller;

import academy.devdojo.anime.mapper.AnimeMapper;
import academy.devdojo.anime.service.AnimeService;
import academy.devdojo.api.AnimeControllerApi;
import academy.devdojo.domain.Anime;
import academy.devdojo.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth") //swagger auth
public class AnimeController implements AnimeControllerApi {
    private final AnimeMapper mapper;
    private final AnimeService service;

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> findAllAnimes(@RequestParam(required = false) String animeName) {
        log.info("Request received to list all animes, param name '{}'", animeName);
        var animes = service.findAll(animeName);
        var response = mapper.toAnimeResponseList(animes);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/paginated")
    public ResponseEntity<PageAnimeGetResponse> findAllAnimesPaginated(
            @Min(0) @Parameter(name = "page", description = "Zero-based page index (0..N)", in = ParameterIn.QUERY) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(1) @Parameter(name = "size", description = "The size of the page to be returned", in = ParameterIn.QUERY) @Valid @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @Parameter(name = "sort", description = "Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort", required = false) List<String> sort,
            @ParameterObject final Pageable pageable) {
        log.info("Request received to list all animes paginated");

        var jpaPageAnimeGetResponse = service.findAllPaginated(pageable);
        var pageAnimeGetResponse = mapper.toPageAnimeGetResponse(jpaPageAnimeGetResponse);

        return ResponseEntity.ok(pageAnimeGetResponse);
    }
//
//    @GetMapping("/paginated") findAll paginated using DTO's
//    public ResponseEntity<Page<AnimeGetResponse>> findAllAnimesPaginated(@ParameterObject Pageable pageable) {
//        log.info("Request received to list all animes paginated");
//
//        var pageAnimeGetResponse = service.findAllPaginated(pageable).map(mapper::toAnimeGetResponse);
//        return ResponseEntity.ok(pageAnimeGetResponse);
//    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findByAnimeId(@PathVariable Long id) {
        log.debug("Request to find anime by id {}", id);

        Anime animeFound = service.findByIdOrThrowNotFoundException(id);
        AnimeGetResponse response = mapper.toAnimeGetResponse(animeFound);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<AnimePostResponse> saveAnime(@RequestBody @Valid AnimePostRequest request) {
        log.info("Trying create anime: {}", request.getName());
        var anime = mapper.toAnime(request);

        Anime animeSaved = service.save(anime);

        var response = mapper.toPostAnimeResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnimeById(@PathVariable Long id) {
        log.info("Deleting anime by id: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<AnimePutResponse> updateAnime(@RequestBody @Valid AnimePutRequest request) {
        var animeToUpdate = mapper.toAnime(request);
        service.update(animeToUpdate);
        var response = mapper.toAnimePutResponse(animeToUpdate);
        return ResponseEntity.ok(response);
    }
}
