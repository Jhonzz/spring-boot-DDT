package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class Producer {
    @EqualsAndHashCode.Include
    //independente se tiver um outro nome ira adequar o mesmo comportamento caso o id seja igual
    private long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
}
