package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerData {

    @Getter
    private final List<Producer> producers = new ArrayList<>();

    {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var saru = Producer.builder().id(2L).name("Saru").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, saru, madhouse));
    }

}
