package academy.devdojo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //<-------------
@ToString
public class Anime {
    @EqualsAndHashCode.Include
    //independente se tiver um outro nome ira adequar o mesmo comportamento caso o id seja igual <-----------
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
