package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Producer {
    private long id;
    @JsonProperty("name")
    private String name;

    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        Producer mappa = new Producer(1L, "Mappa");
        Producer saru = new Producer(2L, "Saru");
        Producer madhouse = new Producer(3L, "Madhouse");
        producers.addAll(List.of(mappa, saru, madhouse));
    }
}
