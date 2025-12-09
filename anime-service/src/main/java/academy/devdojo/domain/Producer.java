package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //<-----------------
public class Producer {
    @EqualsAndHashCode.Include
    //independente se tiver um outro nome ira adequar o mesmo comportamento caso o id seja igual <---------------
    private Long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
}
