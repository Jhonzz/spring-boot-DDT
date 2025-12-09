package academy.devdojo.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProducerPostResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
