package academy.devdojo.DTO.response;

import lombok.Data;

@Data
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
