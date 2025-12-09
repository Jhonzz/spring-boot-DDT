package academy.devdojo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //<-------------
public class Anime {
    @EqualsAndHashCode.Include
    //independente se tiver um outro nome ira adequar o mesmo comportamento caso o id seja igual <-----------
    private Long id;
    private String name;
}
