package academy.devdojo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePutRequest {
    @NotNull(message = "Field 'id' is required when update anime")
    private Long id;
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
