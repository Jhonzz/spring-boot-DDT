package academy.devdojo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProfilePostRequest {
    @NotBlank(message = "Field name can't be empty")
    private String name;
    @NotBlank(message = "Field description can't be empty")
    private String description;
}
