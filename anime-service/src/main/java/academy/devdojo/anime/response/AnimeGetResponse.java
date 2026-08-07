package academy.devdojo.anime.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnimeGetResponse {
    @Schema(example = "1")
    private long id;
    @Schema(example = "Shingeki no Kyojin")
    private String name;
}
