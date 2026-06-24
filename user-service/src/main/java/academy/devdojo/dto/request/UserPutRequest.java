package academy.devdojo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPutRequest {

    @NotNull(message = "The field 'id' is required to update user")
    @Schema(description = "User's id to update", example = "1")
    private Long id;
    @NotBlank(message = "The field 'firstName' is required")//pelo menos um caractere que nao seja null, nao pode ser null nem vazio
    @Schema(description = "User's first name", example = "Uzumaki")
    private String firstName;
    @NotBlank(message = "The field 'lastName' is required")
    @Schema(description = "User's last name", example = "Naruto")
    private String lastName;
    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "E-mail is not valid")
    @Schema(description = "User's email. Must be unique", example = "uzumakiNaruto@gmail.com")
    private String email;
}
