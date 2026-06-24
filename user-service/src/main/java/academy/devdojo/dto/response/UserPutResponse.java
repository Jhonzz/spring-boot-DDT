package academy.devdojo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserPutResponse {
    @Schema(description = "User's id updated", example = "1")
    private Long id;
    @Schema(description = "User's first name", example = "Uzumaki")
    private String firstName;
    @Schema(description = "User's last name", example = "Naruto")
    private String lastName;
    @Schema(description = "User's email. Must be unique", example = "uzumakiNaruto@gmail.com")
    private String email;
}
