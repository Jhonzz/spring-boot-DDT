package academy.devdojo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnimePostRequest {
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
