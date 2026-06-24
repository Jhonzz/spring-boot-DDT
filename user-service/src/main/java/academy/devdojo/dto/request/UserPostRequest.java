package academy.devdojo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPostRequest {
    @NotBlank(message = "The field 'firstName' is required")//pelo menos um caractere que nao seja null, nao pode ser null nem vazio
    @Schema(description = "User's first name", example = "Gojo")
    private String firstName;
    @NotBlank(message = "The field 'lastName' is required")
    @Schema(description = "User's last name", example = "Satoru")
    private String lastName;
    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,10}$", message = "E-mail is not valid")
    @Schema(description = "User's email. Must be unique", example = "gojoSatoru@gmail.com")
    private String email;
}
