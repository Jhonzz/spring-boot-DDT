package academy.devdojo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserPostResponse {
    @Schema(description = "User's id", example = "1")
    private Long id;
}
