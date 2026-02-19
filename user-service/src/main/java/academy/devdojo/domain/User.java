package academy.devdojo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
