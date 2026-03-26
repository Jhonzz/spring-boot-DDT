package academy.devdojo.producer;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProducerPostRequest {
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
