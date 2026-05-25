package academy.devdojo.dto.response;

import lombok.Data;

@Data
public class UserPutResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
