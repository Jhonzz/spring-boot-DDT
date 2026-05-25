package academy.devdojo.dto.response;

import lombok.Data;

@Data
public class ProfileGetResponse {
    private Long id;
    private String name;
    private String description;
}
