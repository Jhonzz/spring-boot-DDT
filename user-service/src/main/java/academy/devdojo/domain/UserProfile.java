package academy.devdojo.domain;

import jakarta.persistence.*;
import lombok.*;

@With
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "UserProfile.fullDetails",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("profile")})
public class UserProfile {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    @ManyToOne(optional = false)
    private Profile profile;
}