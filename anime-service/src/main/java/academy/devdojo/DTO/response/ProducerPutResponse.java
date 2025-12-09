package academy.devdojo.DTO.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProducerPutResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
