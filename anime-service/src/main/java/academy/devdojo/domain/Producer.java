package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //<-----------------
public class Producer {
    @EqualsAndHashCode.Include
    //independente se tiver um outro nome ira adequar o mesmo comportamento caso o id seja igual <---------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("name")
    private String name;
    @Column(nullable = false, insertable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;
}
