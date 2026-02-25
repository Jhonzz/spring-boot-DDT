package academy.devdojo.DTO.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserPutRequest {

    @NotNull(message = "The field 'id' is required to update user")
    private Long id;
    @NotBlank(message = "The field 'firstName' is required")//pelo menos um caractere que nao seja null, nao pode ser null nem vazio
    private String firstName;
    @NotBlank(message = "The field 'lastName' is required")
    private String lastName;
    @NotBlank(message = "The field 'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "E-mail is not valid")
    private String email;
}
