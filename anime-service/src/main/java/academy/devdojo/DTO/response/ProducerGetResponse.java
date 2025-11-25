package academy.devdojo.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProducerGetResponse {
    private String name;
    private long id;
    private LocalDateTime createdAt;
}
