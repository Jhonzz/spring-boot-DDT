package academy.devdojo.mapper;

import academy.devdojo.DTO.request.AnimePutRequest;
import academy.devdojo.DTO.request.AnimePostRequest;
import academy.devdojo.DTO.response.AnimeGetResponse;
import academy.devdojo.DTO.response.AnimePostResponse;
import academy.devdojo.DTO.response.AnimePutResponse;
import academy.devdojo.domain.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Anime toAnime(AnimePostRequest animePostRequest);
    Anime toAnime(AnimePutRequest animePutRequest);

    AnimePostResponse toPostAnimeResponse(Anime anime);

    AnimeGetResponse toAnimeResponse(Anime anime);
    AnimePutResponse toAnimePutResponse(Anime anime);

    List<AnimeGetResponse> toAnimeResponseList(List<Anime> animes);

}
