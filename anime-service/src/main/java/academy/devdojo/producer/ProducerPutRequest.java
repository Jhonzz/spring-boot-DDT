package academy.devdojo.producer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProducerPutRequest {
    @NotNull(message = "Field 'id' is required when update producer")
    private Long id;
    @NotBlank(message = "Field 'name' is required")
    private String name;
}
