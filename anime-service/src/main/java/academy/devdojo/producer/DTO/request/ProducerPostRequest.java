package academy.devdojo.producer.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProducerPostRequest {
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
