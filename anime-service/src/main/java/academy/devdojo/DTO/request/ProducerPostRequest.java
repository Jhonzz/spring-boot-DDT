package academy.devdojo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProducerPostRequest {
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
