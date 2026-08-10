package academy.devdojo.anime.mapper;

import academy.devdojo.domain.Anime;
import academy.devdojo.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {
    Anime toAnime(AnimePostRequest animePostRequest);

    Anime toAnime(AnimePutRequest animePutRequest);

    AnimePostResponse toPostAnimeResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    AnimePutResponse toAnimePutResponse(Anime anime);

    List<AnimeGetResponse> toAnimeResponseList(List<Anime> animes);

    PageAnimeGetResponse toPageAnimeGetResponse(Page<Anime> jpaPageAnimeGetResponse);
}
