package academy.devdojo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "devdojo_user")
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //will return for only necessary return
//    @ToString.Exclude //Exclude for bidirectional relation
//    private List<UserProfile> userProfiles;
}
