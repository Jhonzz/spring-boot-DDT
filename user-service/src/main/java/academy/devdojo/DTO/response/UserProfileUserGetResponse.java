package academy.devdojo.DTO.response;

import lombok.Data;

@Data
public class UserProfileUserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
